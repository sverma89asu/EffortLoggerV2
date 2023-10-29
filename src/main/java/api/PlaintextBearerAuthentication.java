package api;

import javax.net.ssl.HttpsURLConnection;
import java.net.http.HttpRequest;
import java.util.Base64;

public class PlaintextBearerAuthentication implements IAuthenticationService {

    static Base64.Encoder encoder = Base64.getEncoder();

    private final String token;

    public PlaintextBearerAuthentication(String username, String password) {
        StringBuilder builder = new StringBuilder(username);
        builder.append(" ");
        builder.append(password);

        this.token = "Bearer " + encoder.encodeToString(builder.toString().getBytes());
    }

    public PlaintextBearerAuthentication(String token) {
        this.token = "Bearer " + token;
    }

    @Override
    public void includeAuthenticationLayer(HttpRequest.Builder request) {
        request.header("Authorization", this.token);
    }
}
