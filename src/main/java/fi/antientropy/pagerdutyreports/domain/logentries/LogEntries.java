
package fi.antientropy.pagerdutyreports.domain.logentries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "log_entries",
    "limit",
    "offset",
    "more",
    "total"
})
public class LogEntries {

    @JsonProperty("log_entries")
    private List<LogEntry> logEntries = null;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("more")
    private Boolean more;
    @JsonProperty("total")
    private Object total;
    private String incidentId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("log_entries")
    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    @JsonProperty("log_entries")
    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonProperty("more")
    public Boolean getMore() {
        return more;
    }

    @JsonProperty("more")
    public void setMore(Boolean more) {
        this.more = more;
    }

    @JsonProperty("total")
    public Object getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Object total) {
        this.total = total;
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

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

}
