package tu14.api;

import java.net.http.HttpRequest;
import java.util.Base64;

/**
 * Quick and easy fake bearer token authentication type to interface with the server
 */
public class PlaintextBearerAuthentication implements IAuthenticationService {

    static Base64.Encoder encoder = Base64.getEncoder();

    private final String token;

    public PlaintextBearerAuthentication(String username, String password) {
        String builder = username + " " +
                password;

        this.token = "Bearer " + encoder.encodeToString(builder.getBytes());
    }

    public PlaintextBearerAuthentication(String token) {
        this.token = "Bearer " + token;
    }

    @Override
    public void includeAuthenticationLayer(HttpRequest.Builder request) {
        request.header("Authorization", this.token);
    }
}
