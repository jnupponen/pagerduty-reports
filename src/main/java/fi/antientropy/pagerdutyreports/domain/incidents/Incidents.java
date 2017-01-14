
package fi.antientropy.pagerdutyreports.domain.incidents;

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
    "incidents",
    "limit",
    "offset",
    "total",
    "more"
})
public class Incidents {

    @JsonProperty("incidents")
    private List<Incident> incidents = null;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("total")
    private Object total;
    @JsonProperty("more")
    private Boolean more;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("incidents")
    public List<Incident> getIncidents() {
        return incidents;
    }

    @JsonProperty("incidents")
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
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

    @JsonProperty("total")
    public Object getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Object total) {
        this.total = total;
    }

    @JsonProperty("more")
    public Boolean getMore() {
        return more;
    }

    @JsonProperty("more")
    public void setMore(Boolean more) {
        this.more = more;
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
