package fi.antientropy.pagerdutyreports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import fi.antientropy.pagerdutyreports.config.Config;
import fi.antientropy.pagerdutyreports.domain.incidents.Incident;
import fi.antientropy.pagerdutyreports.domain.incidents.Incidents;
import fi.antientropy.pagerdutyreports.service.PagerdutyRestService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PagerdutyRestService.class, Config.class })
public class PagerdutyService {

    @Autowired
    private PagerdutyRestService pagerdutyRestService;

    private Utils serviceLevels = new Utils();

    @Autowired
    private Environment environment;

    @Test
    public void test() throws Exception {
        String token = environment.getProperty("pagerduty.rest.token");

        serviceLevels.init(Arrays.asList(Utils.MONDAY, Utils.TUESDAY, Utils.WEDNESDAY, Utils.THURSDAY, Utils.FRIDAY), "08:00:00.000", "18:00:00.000");

        List<Incidents> incidentss = new ArrayList<>();

        String since = "2017-01-01";
        String until = "2020-01-01";
        Incidents incidents = pagerdutyRestService.getIncident(0, token, since, until, "Europe/Helsinki");
        incidentss.add(incidents);
        int offset = 0;
        while(incidents.getMore()) {
            System.out.println("Get limit: "+incidents.getLimit());
            System.out.println("Get total: "+incidents.getTotal());
            System.out.println("Get offset: "+incidents.getOffset());
            offset = offset + incidents.getLimit();
            incidents = pagerdutyRestService.getIncident(offset, token, since, until, "Europe/Helsinki");
            incidentss.add(incidents);
        }
        List<Incident> manyIncidents = incidentss.stream().map(Incidents::getIncidents).flatMap(List::stream).collect(Collectors.toList());
        System.out.println("before "+manyIncidents.size());
        manyIncidents = manyIncidents.stream()
                .filter(incident -> incident.getService().getSummary().startsWith("Tuotanto"))
                .collect(Collectors.toList());

        System.out.println("after "+manyIncidents.size());
        List<String> incidentIds = manyIncidents.stream().map(Incident::getId).collect(Collectors.toList());
        System.out.println(incidentIds);


        manyIncidents.parallelStream()
        .forEach(incident -> incident.setLogEntries(pagerdutyRestService.getLogEntries(incident.getId(), token)));

        for(Incident incident : manyIncidents) {
            String triggered = incident.getCreatedAt();
            String reacted = serviceLevels.getReactionTime(incident.getLogEntries()).orElse("not found");
            String status = incident.getStatus();
            String resolved = serviceLevels.getResolvedTime(incident.getLogEntries()).orElse("not found");
            String incidentKey = incident.getIncidentKey();
            Integer number  = incident.getIncidentNumber();

            System.out.println(number + " ; "+ incidentKey+ " ; "+triggered + " ; "+reacted+ " ; "+resolved+ " ; "+status);

        }
    }

}
