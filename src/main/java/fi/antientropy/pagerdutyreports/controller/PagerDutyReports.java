package fi.antientropy.pagerdutyreports.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getReport(@RequestParam List<Integer> serviceDays,
            @RequestParam String serviceStart, @RequestParam String serviceStop,
            @RequestParam Optional<String> inclusion, @RequestParam String token,
            @RequestParam String since, @RequestParam String until, @RequestParam String timezone,
            @RequestParam Optional<String> outputDateTimeFormat) {
        StringBuilder builder = new StringBuilder();
        builder.append("IncidentNumber;IncidentKey;IncidentCreatedAt;ReactedToIncidentAt;ResolvedIncidentAt;TimeBetweenIncidentCreatedAndReacted;IncidentStatus;IncidentNotes").append("\n");
        try {
            DateTimeFormatter dtf = outputDateTimeFormat.map(DateTimeFormat::forPattern).orElse(ISODateTimeFormat.dateTime());

            List<Integer> days = Arrays.asList(Utils.MONDAY, Utils.TUESDAY, Utils.WEDNESDAY, Utils.THURSDAY, Utils.FRIDAY);
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

            List<String> incidentIds = allIncidents.stream().map(Incident::getId).collect(Collectors.toList());
            System.out.println(incidentIds);


            allIncidents.parallelStream()
            .forEach(incident -> incident.setLogEntries(pagerdutyRestService.getLogEntries(incident.getId(), token)));

            for(Incident incident : allIncidents) {

                String triggered = dtf.print(DateTime.parse(incident.getCreatedAt()));
                String reacted = serviceLevels.getReactionTime(incident.getLogEntries());
                String status = incident.getStatus();
                String resolved = dtf.print(DateTime.parse(serviceLevels.getResolvedTime(incident.getLogEntries())));
                String incidentKey = incident.getIncidentKey();
                Integer number  = incident.getIncidentNumber();
                String reactionTimestamp = dtf.print(DateTime.parse(serviceLevels.getReactionTimeStamp(incident.getLogEntries())));
                String messages = serviceLevels.getMessages(incident.getLogEntries());

                String line = number + SEP+ incidentKey+ SEP+triggered +SEP+ reactionTimestamp + SEP+resolved+SEP+reacted+ SEP+status + SEP +messages;
                builder.append(line);
                builder.append("\r\n");

            }
            return new ResponseEntity<String>(builder.toString(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Unable to fetch report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
