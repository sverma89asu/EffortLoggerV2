package tu14.api;

import java.net.http.HttpRequest;

public interface IAuthenticationService {

    /**
     * Modify Request builder to include Authentication information
     */
    void includeAuthenticationLayer(HttpRequest.Builder connection);
}
