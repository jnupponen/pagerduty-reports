
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
    "incident_number",
    "description",
    "created_at",
    "status",
    "pending_actions",
    "incident_key",
    "service",
    "assignments",
    "acknowledgements",
    "last_status_change_at",
    "last_status_change_by",
    "first_trigger_log_entry",
    "escalation_policy",
    "privilege",
    "teams",
    "alert_counts",
    "impacted_services",
    "is_mergeable",
    "importance",
    "resolve_reason",
    "incidents_responders",
    "responder_requests",
    "urgency",
    "id",
    "type",
    "summary",
    "self",
    "html_url"
})
public class Incident {

    @JsonProperty("incident_number")
    private Integer incidentNumber;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("pending_actions")
    private List<Object> pendingActions = null;
    @JsonProperty("incident_key")
    private String incidentKey;
    @JsonProperty("service")
    private Service_ service;
    @JsonProperty("assignments")
    private List<Object> assignments = null;
    @JsonProperty("acknowledgements")
    private List<Object> acknowledgements = null;
    @JsonProperty("last_status_change_at")
    private String lastStatusChangeAt;
    @JsonProperty("last_status_change_by")
    private LastStatusChangeBy lastStatusChangeBy;
    @JsonProperty("first_trigger_log_entry")
    private FirstTriggerLogEntry firstTriggerLogEntry;
    @JsonProperty("escalation_policy")
    private EscalationPolicy escalationPolicy;
    @JsonProperty("privilege")
    private Object privilege;
    @JsonProperty("teams")
    private List<Object> teams = null;
    @JsonProperty("alert_counts")
    private AlertCounts alertCounts;
    @JsonProperty("impacted_services")
    private List<ImpactedService> impactedServices = null;
    @JsonProperty("is_mergeable")
    private Boolean isMergeable;
    @JsonProperty("importance")
    private Object importance;
    @JsonProperty("resolve_reason")
    private Object resolveReason;
    @JsonProperty("incidents_responders")
    private List<Object> incidentsResponders = null;
    @JsonProperty("responder_requests")
    private List<Object> responderRequests = null;
    @JsonProperty("urgency")
    private String urgency;
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("self")
    private String self;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("incident_number")
    public Integer getIncidentNumber() {
        return incidentNumber;
    }

    @JsonProperty("incident_number")
    public void setIncidentNumber(Integer incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("pending_actions")
    public List<Object> getPendingActions() {
        return pendingActions;
    }

    @JsonProperty("pending_actions")
    public void setPendingActions(List<Object> pendingActions) {
        this.pendingActions = pendingActions;
    }

    @JsonProperty("incident_key")
    public String getIncidentKey() {
        return incidentKey;
    }

    @JsonProperty("incident_key")
    public void setIncidentKey(String incidentKey) {
        this.incidentKey = incidentKey;
    }

    @JsonProperty("service")
    public Service_ getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(Service_ service) {
        this.service = service;
    }

    @JsonProperty("assignments")
    public List<Object> getAssignments() {
        return assignments;
    }

    @JsonProperty("assignments")
    public void setAssignments(List<Object> assignments) {
        this.assignments = assignments;
    }

    @JsonProperty("acknowledgements")
    public List<Object> getAcknowledgements() {
        return acknowledgements;
    }

    @JsonProperty("acknowledgements")
    public void setAcknowledgements(List<Object> acknowledgements) {
        this.acknowledgements = acknowledgements;
    }

    @JsonProperty("last_status_change_at")
    public String getLastStatusChangeAt() {
        return lastStatusChangeAt;
    }

    @JsonProperty("last_status_change_at")
    public void setLastStatusChangeAt(String lastStatusChangeAt) {
        this.lastStatusChangeAt = lastStatusChangeAt;
    }

    @JsonProperty("last_status_change_by")
    public LastStatusChangeBy getLastStatusChangeBy() {
        return lastStatusChangeBy;
    }

    @JsonProperty("last_status_change_by")
    public void setLastStatusChangeBy(LastStatusChangeBy lastStatusChangeBy) {
        this.lastStatusChangeBy = lastStatusChangeBy;
    }

    @JsonProperty("first_trigger_log_entry")
    public FirstTriggerLogEntry getFirstTriggerLogEntry() {
        return firstTriggerLogEntry;
    }

    @JsonProperty("first_trigger_log_entry")
    public void setFirstTriggerLogEntry(FirstTriggerLogEntry firstTriggerLogEntry) {
        this.firstTriggerLogEntry = firstTriggerLogEntry;
    }

    @JsonProperty("escalation_policy")
    public EscalationPolicy getEscalationPolicy() {
        return escalationPolicy;
    }

    @JsonProperty("escalation_policy")
    public void setEscalationPolicy(EscalationPolicy escalationPolicy) {
        this.escalationPolicy = escalationPolicy;
    }

    @JsonProperty("privilege")
    public Object getPrivilege() {
        return privilege;
    }

    @JsonProperty("privilege")
    public void setPrivilege(Object privilege) {
        this.privilege = privilege;
    }

    @JsonProperty("teams")
    public List<Object> getTeams() {
        return teams;
    }

    @JsonProperty("teams")
    public void setTeams(List<Object> teams) {
        this.teams = teams;
    }

    @JsonProperty("alert_counts")
    public AlertCounts getAlertCounts() {
        return alertCounts;
    }

    @JsonProperty("alert_counts")
    public void setAlertCounts(AlertCounts alertCounts) {
        this.alertCounts = alertCounts;
    }

    @JsonProperty("impacted_services")
    public List<ImpactedService> getImpactedServices() {
        return impactedServices;
    }

    @JsonProperty("impacted_services")
    public void setImpactedServices(List<ImpactedService> impactedServices) {
        this.impactedServices = impactedServices;
    }

    @JsonProperty("is_mergeable")
    public Boolean getIsMergeable() {
        return isMergeable;
    }

    @JsonProperty("is_mergeable")
    public void setIsMergeable(Boolean isMergeable) {
        this.isMergeable = isMergeable;
    }

    @JsonProperty("importance")
    public Object getImportance() {
        return importance;
    }

    @JsonProperty("importance")
    public void setImportance(Object importance) {
        this.importance = importance;
    }

    @JsonProperty("resolve_reason")
    public Object getResolveReason() {
        return resolveReason;
    }

    @JsonProperty("resolve_reason")
    public void setResolveReason(Object resolveReason) {
        this.resolveReason = resolveReason;
    }

    @JsonProperty("incidents_responders")
    public List<Object> getIncidentsResponders() {
        return incidentsResponders;
    }

    @JsonProperty("incidents_responders")
    public void setIncidentsResponders(List<Object> incidentsResponders) {
        this.incidentsResponders = incidentsResponders;
    }

    @JsonProperty("responder_requests")
    public List<Object> getResponderRequests() {
        return responderRequests;
    }

    @JsonProperty("responder_requests")
    public void setResponderRequests(List<Object> responderRequests) {
        this.responderRequests = responderRequests;
    }

    @JsonProperty("urgency")
    public String getUrgency() {
        return urgency;
    }

    @JsonProperty("urgency")
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

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
    public String getHtmlUrl() {
        return htmlUrl;
    }

    @JsonProperty("html_url")
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
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
