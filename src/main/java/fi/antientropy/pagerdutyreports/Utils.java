package fi.antientropy.pagerdutyreports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.antientropy.pagerdutyreports.domain.logentries.LogEntries;
import fi.antientropy.pagerdutyreports.domain.logentries.LogEntry;

public class Utils implements ServiceLevels {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public static final PeriodFormatter HOURS_MINUTES_SECONDS = new PeriodFormatterBuilder()
    .printZeroAlways()
    .minimumPrintedDigits(2)
    .appendHours()
    .appendSeparator(":")
    .appendMinutes()
    .appendSeparator(":")
    .appendSeconds()
    .toFormatter();

    private LocalTime serviceStart;
    private LocalTime serviceStop;

    List<Integer> serviceDays;

    public void init(List<Integer> serviceDays, String serviceStart, String serviceStop) {
        this.serviceStart = LocalTime.parse(serviceStart);
        this.serviceStop = LocalTime.parse(serviceStop);

        this.serviceDays = serviceDays;
    }
    @Override
    public String getReactionTime(LogEntries logEntries) throws Exception {
        DateTime reaction = getReactionDateTime(logEntries);

        List<LogEntry> triggerLogEntries = logEntries.getLogEntries().stream()
                .filter(logEntry -> "trigger_log_entry".equals(logEntry.getType()))
                .collect(Collectors.toList());

         Collections.sort(triggerLogEntries, (first, second) -> {
            boolean firstIsBefore = DateTime.parse(first.getCreatedAt()).isBefore(DateTime.parse(second.getCreatedAt()));
             return firstIsBefore ? -1 : 1;

         });

         DateTime triggered = triggerLogEntries.stream()
            .findFirst()
            .map(LogEntry::getCreatedAt)
            .map(DateTime::parse)
            .orElseThrow(() -> new Exception("time not defined in triggered"));

         triggered = adjustByServiceDays(triggered);
         if(triggered.toLocalTime().isAfter(serviceStop)) {
             LocalDate next = triggered.toLocalDate().plusDays(1);
             triggered = next.toDateTime(serviceStart, DateTimeZone.forOffsetHours(2));
         }
         if(triggered.toLocalTime().isBefore(serviceStart)) {
             triggered = triggered.toLocalDate().toDateTime(serviceStart, DateTimeZone.forOffsetHours(2));
         }

         Long days = getAmountOfNonServiceDays(triggered, reaction);
         Long nights = getAmountOfNonServiceNightsBetween(triggered, reaction);
         Duration duration = new Duration(triggered.toDateTime(DateTimeZone.UTC), reaction.toDateTime(DateTimeZone.UTC))
         .minus(new Duration(days*24*60*60*1000))
         .minus(new Duration(nights*14*60*60*1000));
         if(duration.isShorterThan(new Duration(0))) {
             duration = new Duration(0);
         }
         String durationString = duration.toPeriod().toString(HOURS_MINUTES_SECONDS);
         return durationString;
    }


    private DateTime adjustByServiceDays(DateTime original) {
        if(!serviceDays.contains(original.getDayOfWeek())) {
            LOG.error("Outside service days");
            int old = original.getDayOfWeek();
            DateTime next = original.plusDays(8 - old);
            original = next.toLocalDate().toDateTime(serviceStart, DateTimeZone.forOffsetHours(2));
            return original;
        }
        else {
            return original;
        }
    }

    private Long getAmountOfNonServiceNightsBetween(DateTime first, DateTime second) {
        List<LocalDate> dates = new ArrayList<LocalDate>();
        int days = Days.daysBetween(first, second).getDays();
        for (int i=0; i < days; i++) {
            LocalDate d = first.toLocalDate().withFieldAdded(DurationFieldType.days(), i);
            dates.add(d);
        }


        Long i = dates.stream()
                .filter(date -> serviceDays.contains(date.getDayOfWeek()))
                .count();
        return i;
    }

    private Long getAmountOfNonServiceDays(DateTime first, DateTime second) {

        List<LocalDate> dates = new ArrayList<LocalDate>();
        int days = Days.daysBetween(first, second).getDays()+1;
        for (int i=0; i < days; i++) {
            LocalDate d = first.toLocalDate().withFieldAdded(DurationFieldType.days(), i);
            dates.add(d);
        }

        Long i = dates.stream()
            .filter(date -> !serviceDays.contains(date.getDayOfWeek()))
            .count();


        return i;
    }


    @Override
    public String getResolvedTime(LogEntries logEntries) throws Exception {
        List<LogEntry> resolved = logEntries.getLogEntries().stream()
                .filter(logEntry -> "resolve_log_entry".equals(logEntry.getType()))
                .collect(Collectors.toList());

         Collections.sort(resolved, (first, second) -> {
            boolean firstIsBefore = DateTime.parse(first.getCreatedAt()).isBefore(DateTime.parse(second.getCreatedAt()));
             return firstIsBefore ? -1 : 1;
         });

         Collections.reverse(resolved);
         return resolved.stream().findFirst().map(LogEntry::getCreatedAt).orElse("not_known");
    }
    @Override
    public String getReactionTimeStamp(LogEntries logEntries) throws Exception {
        return getReactionDateTime(logEntries).toString();
    }

    private DateTime getReactionDateTime(LogEntries logEntries) throws Exception {
        List<LogEntry> reactions = logEntries.getLogEntries().stream()
                .filter(logEntry -> "annotate_log_entry".equals(logEntry.getType()) || "acknowledge_log_entry".equals(logEntry.getType()) || "resolve_log_entry".equals(logEntry.getType()))
                .collect(Collectors.toList());

         Collections.sort(reactions, (first, second) -> {
            boolean firstIsBefore = DateTime.parse(first.getCreatedAt()).isBefore(DateTime.parse(second.getCreatedAt()));
             return firstIsBefore ? -1 : 1;

         });

         DateTime reaction = reactions.stream()
            .findFirst()
            .map(LogEntry::getCreatedAt)
            .map(DateTime::parse)
            .orElseThrow(() -> new Exception("time not defined in reactions"));

         return reaction;
    }
    @Override
    public String getMessages(LogEntries logEntries) throws Exception {
        List<LogEntry> messages = logEntries.getLogEntries().stream()
                .filter(logEntry -> "annotate_log_entry".equals(logEntry.getType()))
                .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();

        for(LogEntry logEntry : messages) {
            if(builder.length() != 0) {
                builder.append("\n");
            }
            builder.append(logEntry.getChannel().getSummary());
        }
        return builder.toString().replace(";", "SEMICOLON_REMOVED").replace("\r", "").replace("\n\n", "|").replace("\n", "");
    }

}
