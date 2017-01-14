
package fi.antientropy.pagerdutyreports.domain.logentries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "summary",
    "self",
    "html_url",
    "created_at",
    "agent",
    "channel",
    "service",
    "incident",
    "teams",
    "contexts",
    "event_details",
    "assignees",
    "user"
})
public class LogEntry {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("self")
    private String self;
    @JsonProperty("html_url")
    private Object htmlUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("agent")
    private Agent agent;
    @JsonProperty("channel")
    private Channel channel;
    @JsonProperty("service")
    private Service service;
    @JsonProperty("incident")
    private Incident incident;
    @JsonProperty("teams")
    private List<Object> teams = null;
    @JsonProperty("contexts")
    private List<Object> contexts = null;
    @JsonProperty("event_details")
    private EventDetails eventDetails;
    @JsonProperty("assignees")
    private List<Assignee> assignees = null;
    @JsonProperty("user")
    private User user;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @JsonProperty("html_url")
    public Object getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public void setHtmlUrl(Object htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("agent")
    public Agent getAgent() {
        return agent;
    }

    @JsonProperty("agent")
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @JsonProperty("channel")
    public Channel getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @JsonProperty("service")
    public Service getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(Service service) {
        this.service = service;
    }

    @JsonProperty("incident")
    public Incident getIncident() {
        return incident;
    }

    @JsonProperty("incident")
    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    @JsonProperty("teams")
    public List<Object> getTeams() {
        return teams;
    }

    @JsonProperty("teams")
    public void setTeams(List<Object> teams) {
        this.teams = teams;
    }

    @JsonProperty("contexts")
    public List<Object> getContexts() {
        return contexts;
    }

    @JsonProperty("contexts")
    public void setContexts(List<Object> contexts) {
        this.contexts = contexts;
    }

    @JsonProperty("event_details")
    public EventDetails getEventDetails() {
        return eventDetails;
    }

    @JsonProperty("event_details")
    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    @JsonProperty("assignees")
    public List<Assignee> getAssignees() {
        return assignees;
    }

    @JsonProperty("assignees")
    public void setAssignees(List<Assignee> assignees) {
        this.assignees = assignees;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
