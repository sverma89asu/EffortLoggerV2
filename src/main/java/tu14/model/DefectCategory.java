package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;

public class DefectCategory implements IRawImplementer<DefectCategory> {
    @JsonIgnore
    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private DefectCategory() {}
    public DefectCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public DefectCategory construct(JsonNode data) {
        // TODO catch this; it could throw a runtime exception
        this.id = data.get("id").asInt();
        this.name = data.get("name").asText();
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
