package org.yugzan.linebot.model;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author yongzan
 * @date 2016/11/10
 */
public class PointResource {
    
    private String measurement;
    
    private Map<String, Object> fields;
    
    @JsonInclude(Include.NON_NULL)
    private Map<String, String> tags;
    
    @JsonInclude(Include.NON_NULL)
    private Long time;
    
    @JsonInclude(Include.NON_NULL)
    private TimeUnit precision = TimeUnit.NANOSECONDS;


    public PointResource() {}

    public PointResource(String measurement, Map<String, String> tags, Long time,
            TimeUnit precision, Map<String, Object> fields) {
        super();
        this.measurement = measurement;
        this.tags = tags;
        this.time = time;
        this.precision = precision;
        this.fields = fields;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public TimeUnit getPrecision() {
        return precision;
    }

    public void setPrecision(TimeUnit precision) {
        this.precision = precision;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
