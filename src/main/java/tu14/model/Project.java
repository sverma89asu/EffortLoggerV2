package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;

/**
 * Author: Shikha Verma
 * Date: 2023-11-10
 * Description: This Java class will contain information of project
 */
public class Project implements IRawImplementer<Project> {
    @JsonIgnore
    public int id;
    public String name;

    private Project() {}
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public Project construct(JsonNode data) {
        // TODO catch this; it could throw a runtime exception
        this.id = data.get("id").asInt();
        this.name = data.get("name").asText();
        return this;
    }
}
