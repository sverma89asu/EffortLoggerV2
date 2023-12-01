package tu14.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import tu14.api.IRawImplementer;

public class RawUserData implements IRawImplementer<RawUserData> {

    @JsonIgnore
    public int id;
    public String username;
    public String password;

    private RawUserData() {}

    public RawUserData(UserData datum, String password) {
        this.id = datum.getId();
        this.username = datum.getUsername();
        this.password = password;
    }

    @Override
    public RawUserData construct(JsonNode data) {
        // TODO catch this; it could throw a runtime exception
        this.id = data.get("id").asInt();
        this.username = data.get("username").asText();
        this.password = data.get("password").asText();

        return this;
    }
}
