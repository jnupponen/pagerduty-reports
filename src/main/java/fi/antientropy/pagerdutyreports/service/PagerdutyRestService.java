package fi.antientropy.pagerdutyreports.service;

import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fi.antientropy.pagerdutyreports.domain.incidents.Incidents;
import fi.antientropy.pagerdutyreports.domain.logentries.LogEntries;

@Service
public class PagerdutyRestService {
    @Value("${pagerduty.rest.url}")
    private String pagerdutyRestUrl;

    @Autowired
    private RestTemplate rest;

    private static final Logger LOG = Logger.getLogger(PagerdutyRestService.class);

    public HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Token token=" + token);
        headers.add("Accept", "application/vnd.pagerduty+json;version=2");
        return headers;
    }

    public Incidents getIncident(Integer offset, String token, String since, String until, String timezone) {
        try {
            HttpHeaders headers = this.createHeaders(token);
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<Incidents> response = rest.exchange(pagerdutyRestUrl+"/incidents?since={since}&until={until}&limit=100&offset={offset}&time_zone={timezone}", HttpMethod.GET, entity, Incidents.class, since, until, String.valueOf(offset), timezone);
            Incidents responseIncident = response.getBody();

            return responseIncident;
        } catch (Exception e) {
            LOG.error("Unable request data from pagerduty api",e);
            throw e;
        }
    }
    public LogEntries getLogEntries(String incidentId, String token) {
        try {
            HttpHeaders headers = this.createHeaders(token);
            HttpEntity entity = new HttpEntity(headers);

            String timezone = Calendar.getInstance(TimeZone.getDefault()).getTimeZone().getID();

            ResponseEntity<LogEntries> response = rest.exchange(pagerdutyRestUrl+"/incidents/{id}/log_entries?time_zone={timezone}&include%5B%5D=incidents", HttpMethod.GET, entity, LogEntries.class, String.valueOf(incidentId), timezone);

            LogEntries responseIncident = response.getBody();

            return responseIncident;
        } catch (Exception e) {
            LOG.error("Unable request data from pagerduty api",e);
            throw e;
        }
    }
}
