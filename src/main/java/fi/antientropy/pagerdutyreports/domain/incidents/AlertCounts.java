
package fi.antientropy.pagerdutyreports.domain.incidents;

import java.util.HashMap;
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
    "all",
    "triggered",
    "resolved"
})
public class AlertCounts {

    @JsonProperty("all")
    private Integer all;
    @JsonProperty("triggered")
    private Integer triggered;
    @JsonProperty("resolved")
    private Integer resolved;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("all")
    public Integer getAll() {
        return all;
    }

    @JsonProperty("all")
    public void setAll(Integer all) {
        this.all = all;
    }

    @JsonProperty("triggered")
    public Integer getTriggered() {
        return triggered;
    }

    @JsonProperty("triggered")
    public void setTriggered(Integer triggered) {
        this.triggered = triggered;
    }

    @JsonProperty("resolved")
    public Integer getResolved() {
        return resolved;
    }

    @JsonProperty("resolved")
    public void setResolved(Integer resolved) {
        this.resolved = resolved;
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
