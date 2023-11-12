package tu14.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.text.StringEscapeUtils;
import tu14.api.IRawImplementer;
import tu14.api.exceptions.APITransformException;
import tu14.data.IExportableData;

public class DefectLog implements IRawImplementer<DefectLog>, IExportableData {

    @JsonIgnore
    protected long id;

    public String name;
    public String description;
    public String fix = "";
    public boolean status = false;
    public int lifeCycleIncluded;
    public Integer lifeCycleExcluded = null;
    public int defectCategory;
    public int project;


    DefectLog() {}

    public DefectLog(String name, String description, int project, int lifeCycleIncluded,
               int defectCategory) {

        this.name = name;
        this.description = description;
        this.project = project;
        this.lifeCycleIncluded = lifeCycleIncluded;
        this.defectCategory = defectCategory;
    }

    @Override
    public DefectLog construct(JsonNode data) throws APITransformException {
        this.id = data.get("id").asLong();
        this.name = data.get("name").asText();
        this.description = data.get("description").asText();
        this.fix = data.get("fix").asText();
        this.status = data.get("status").asBoolean();
        this.lifeCycleIncluded = data.get("lifeCycleIncluded").asInt();
        this.lifeCycleExcluded = data.get("lifeCycleExcluded").asInt();
        this.project = data.get("project").asInt();
        return this;
    }

    @Override
    public String toCSVHeader() {
        return "id, name, description, fix, status, lifecycle included, lifecycle excluded, " +
                "defect category, project";
    }

    @Override
    public String toCSV() {
        return String.format("%d, %s, %s, %s, %b, %d, %d, %d, %d", id,
                             StringEscapeUtils.escapeCsv(name),
                             StringEscapeUtils.escapeCsv(description),
                             StringEscapeUtils.escapeCsv(fix), status, lifeCycleIncluded,
                             lifeCycleExcluded, defectCategory, project);
    }

    @JsonIgnore
    public long getId() {
        return id;
    }
}
