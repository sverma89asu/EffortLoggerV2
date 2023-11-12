package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;
import tu14.api.exceptions.APITransformException;

public class PlanningPokerSession implements IRawImplementer<PlanningPokerSession> {

    @JsonIgnore
    public long id;

    public String name;
    public int storyId;

    PlanningPokerSession() {}

    public PlanningPokerSession(String name, int storyId) {

        this.name = name;
        this.storyId = storyId;
    }

    @Override
    public PlanningPokerSession construct(JsonNode data) throws APITransformException {
        id = data.get("id").asLong();
        name = data.get("name").asText();
        storyId = data.get("storyId").asInt();

        return this;
    }
}
