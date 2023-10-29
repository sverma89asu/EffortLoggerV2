package api;

import javax.net.ssl.HttpsURLConnection;
import java.net.http.HttpRequest;

public interface IAuthenticationService {

    void includeAuthenticationLayer(HttpRequest.Builder connection);
}
