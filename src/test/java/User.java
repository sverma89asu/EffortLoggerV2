import tu14.api.IRawImplementer;
import tu14.api.exceptions.APITransformException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

public class User implements IRawImplementer<User> {

    @JsonIgnore
    protected long id;

    protected String username;

    protected String password;

    User() {
        this(null, null);
    }

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "uname: " + username + "; pwd: " + password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    @Override
    public User construct(JsonNode data) throws APITransformException {
        try {
            this.id = data.get("id").asLong();
            this.username = data.get("username").asText();
            this.password = data.get("password").asText();
        } catch (RuntimeException e) {
            throw new APITransformException(e);
        }

        return this;
    }
}
