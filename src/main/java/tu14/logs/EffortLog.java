package tu14.logs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tu14.api.IRawImplementer;
import tu14.api.exceptions.APITransformException;

import java.time.Duration;
import java.time.Instant;

public class EffortLog implements IRawImplementer<EffortLog> {

    private int id;
    private Instant start;
    private Instant end;

    private long duration; // NOTE: in milliseconds

    // Not encapsulated because we aren't restricting the thing
    public String lifeCycle;
    public String effortCategory;
    public String deliverable;

    private EffortLog() {}

    /**
     * Note: id field remains invalid
     */
    public EffortLog(Instant start, Instant end, String lifeCycle, String effortCategory, String deliverable) {
        this.start = start;
        this.end = end;
        this.lifeCycle = lifeCycle;
        this.effortCategory = effortCategory;
        this.deliverable = deliverable;
        recalculateDuration();
    }

    public static EffortLog copy(EffortLog log) {
        EffortLog copy = new EffortLog(log.start, log.end, log.lifeCycle, log.effortCategory, log.deliverable);
        copy.id = log.getId();

        return copy;
    }

    @Override
    public EffortLog construct(JsonNode data) {
        this.start = Instant.parse(data.get("start").asText());
        this.end = Instant.parse(data.get("start").asText());
        recalculateDuration();

        this.lifeCycle = data.get("lifeCycle").asText();
        this.effortCategory = data.get("effortCategory").asText();
        this.deliverable = data.get("deliverable").asText();
        this.id = data.get("id").asInt();

        return this;
    }

    private void recalculateDuration() {
        duration = Duration.between(getStart(), getEnd()).toMillis();
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public double getDuration() {
        return duration;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
        recalculateDuration();
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
        recalculateDuration();
    }

    //This is for tableView and it's ridiculous
    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty startProperty() {
        return new SimpleStringProperty(start.toString());
    }

    public StringProperty durationProperty() {
        Duration d = Duration.ofMillis(duration);

        return new SimpleStringProperty(String.format("%d:%02d:%02d", d.toHours(),
                                                      d.toMinutesPart(), d.toSecondsPart()));
    }

    public StringProperty lifecycleProperty() {
        return new SimpleStringProperty(lifeCycle);
    }

    public StringProperty effortProperty() {
        return new SimpleStringProperty(effortCategory);
    }

    public StringProperty deliverableProperty() {
        return new SimpleStringProperty(deliverable);
    }
}
