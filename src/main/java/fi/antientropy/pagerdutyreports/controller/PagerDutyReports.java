package fi.antientropy.pagerdutyreports.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.antientropy.pagerdutyreports.Utils;
import fi.antientropy.pagerdutyreports.domain.incidents.Incident;
import fi.antientropy.pagerdutyreports.domain.incidents.Incidents;
import fi.antientropy.pagerdutyreports.service.PagerdutyRestService;

@Controller
public class PagerDutyReports {

    private static final String SEP = ";";

    @Autowired
    private PagerdutyRestService pagerdutyRestService;

    private Utils serviceLevels = new Utils();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<?> getReport(@RequestParam List<Integer> serviceDays,
            @RequestParam String serviceStart, @RequestParam String serviceStop,
            @RequestParam Optional<String> inclusion, @RequestParam String token,
            @RequestParam String since, @RequestParam String until, @RequestParam String timezone,
            @RequestParam Optional<String> outputDateTimeFormat) {

        Document document = Jsoup.parse("");
        Element pre = document.getElementsByTag("body").first().appendElement("pre");

        String header = "Service;IncidentNumber;IncidentKey;IncidentCreatedAt;ReactedToIncidentAt;ResolvedIncidentAt;TimeBetweenIncidentCreatedAndReacted;IncidentStatus;IncidentDescription;IncidentNotes";
        pre.appendText(header).appendText("\n");
        try {
            DateTimeFormatter dtf = outputDateTimeFormat.map(DateTimeFormat::forPattern).orElse(ISODateTimeFormat.dateTime());

            serviceLevels.init(serviceDays, serviceStart, serviceStop);

            List<Incidents> incidentss = new ArrayList<>();
            Incidents incidents = pagerdutyRestService.getIncident(0, token, since, until, timezone);
            incidentss.add(incidents);
            int offset = 0;
            while(incidents.getMore()) {
                offset = offset + incidents.getLimit();
                incidents = pagerdutyRestService.getIncident(offset, token, since, until, timezone);
                incidentss.add(incidents);
            }
            List<Incident> allIncidents = incidentss.stream()
                    .map(Incidents::getIncidents)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            allIncidents = allIncidents.stream()
                    .filter(incident -> incident.getService().getSummary().startsWith(inclusion.orElse("")))
                    .collect(Collectors.toList());

            allIncidents.parallelStream()
            .forEach(incident -> incident.setLogEntries(pagerdutyRestService.getLogEntries(incident.getId(), token, timezone)));

            for(Incident incident : allIncidents) {

                String service = incident.getService().getSummary();
                String triggered = dtf.print(DateTime.parse(incident.getCreatedAt()));
                String reacted = serviceLevels.getReactionTime(incident.getLogEntries()).orElse("");
                String status = incident.getStatus();
                String resolved = serviceLevels.getResolvedTime(incident.getLogEntries())
                        .map(DateTime::parse)
                        .map(value -> dtf.print(value))
                        .orElse("");
                String incidentKey = incident.getIncidentKey();
                Integer number  = incident.getIncidentNumber();
                String reactionTimestamp = serviceLevels.getReactionTimeStamp(incident.getLogEntries())
                        .map(DateTime::parse)
                        .map(value -> dtf.print(value))
                        .orElse("");

                String description = incident.getDescription();

                String messages = serviceLevels.getMessages(incident.getLogEntries()).orElse("");

                String line =
                        service + SEP
                        + number + SEP
                        + incidentKey + SEP
                        + triggered +SEP
                        + reactionTimestamp + SEP
                        + resolved + SEP
                        + reacted + SEP
                        + status  + SEP
                        + StringEscapeUtils.escapeCsv(description) + SEP
                        + StringEscapeUtils.escapeCsv(messages);

                pre.appendText(line);
                pre.appendText("\n");

            }
            return new ResponseEntity<String>(document.html(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Unable to fetch report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
