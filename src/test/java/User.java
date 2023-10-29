import api.IRawImplementer;
import api.exceptions.APITransformException;
import com.fasterxml.jackson.databind.JsonNode;

public class User implements IRawImplementer<User> {

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
            this.username = data.get("username").asText();
            this.password = data.get("password").asText();
        } catch (RuntimeException e) {
            throw new APITransformException(e);
        }

        return this;
    }
}
