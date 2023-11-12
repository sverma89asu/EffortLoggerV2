package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EffortLog implements IRawImplementer<EffortLog> {
    @JsonIgnore
    public int id;

    public Timestamp start;

    public Timestamp end;

    public int lifeCycle;

    public int effortCategory;

    public int deliverable;

    public int project;



    private EffortLog() {}

    public EffortLog(int id, Timestamp start, Timestamp end, int lifeCycle, int effortCategory, int deliverable, int project) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.lifeCycle = lifeCycle;
        this.effortCategory = effortCategory;
        this.deliverable = deliverable;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(int lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public int getEffortCategory() {
        return effortCategory;
    }

    public void setEffortCategory(int effortCategory) {
        this.effortCategory = effortCategory;
    }

    public int getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(int deliverable) {
        this.deliverable = deliverable;
    }

    @Override
    public EffortLog construct(JsonNode data) {
        // TODO catch this; it could throw a runtime exception
        this.id = data.asInt(Integer.parseInt("id"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(data.asText("start"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.start = new java.sql.Timestamp(parsedDate.getTime());
        try {
            parsedDate = dateFormat.parse(data.asText("end"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.end = new java.sql.Timestamp(parsedDate.getTime());;
        this.lifeCycle = data.asInt(Integer.parseInt("lifeCycle"));
        this.effortCategory = data.asInt(Integer.parseInt("effortCategory"));
        this.deliverable = data.asInt(Integer.parseInt("deliverable"));
        this.project = data.asInt(Integer.parseInt("project"));
        return this;
    }

    @Override
    public String toString() {
        return "effort log";
    }
}
