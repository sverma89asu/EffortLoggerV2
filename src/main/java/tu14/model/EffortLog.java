package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;
import tu14.data.IExportableData;

import java.time.Duration;
import java.time.Instant;

/**
 * Author: Shikha Verma
 * Date: 2023-11-10
 * Description: This Java class will contain information of effort log instance
 */
public class EffortLog implements IRawImplementer<EffortLog>, IExportableData {

    // Not encapsulated because we aren't restricting the thing
    public int lifeCycle;
    public int effortCategory;
    public int deliverable;
    public int project;
    private int id;
    public int user = 0;
    private Instant start;
    private Instant end;
    private Duration duration; // NOTE: in milliseconds

    public EffortLog() {}

    /**
     * Note: id field remains invalid
     */
    public EffortLog(Instant start, Instant end, int lifeCycle, int effortCategory,
                     int deliverable, int project) {
        this.start = start;
        this.end = end;
        this.lifeCycle = lifeCycle;
        this.effortCategory = effortCategory;
        this.deliverable = deliverable;
        this.project = project;
        recalculateDuration();
    }

    @Override
    public EffortLog construct(JsonNode data) {
        this.start = Instant.parse(data.get("start").asText());
        this.end = Instant.parse(data.get("start").asText());
        recalculateDuration();

        this.lifeCycle = data.get("lifeCycle").asInt();
        this.effortCategory = data.get("effortCategory").asInt();
        this.deliverable = data.get("deliverable").asInt();
        this.project = data.get("project").asInt();
        this.id = data.get("id").asInt();

        return this;
    }

    private void recalculateDuration() {
        duration = Duration.between(getStart(), getEnd());
    }

    @JsonIgnore
    public int getId() {
        return id;
    }


    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    //To be used for export function
    @Override
    public String toCSVHeader() {
        return "id, start, end, life cycle, effort category, deliverable, project";
    }

    //To be used for export function
    @Override
    public String toCSV() {
        return String.format("%d, %s, %s, %d, %d, %d, %d", id, start.toString(), end.toString(),
                             lifeCycle, effortCategory, deliverable, project);
    }
}
