package tu14.api;

import java.net.http.HttpRequest;

public interface IAuthenticationService {

    void includeAuthenticationLayer(HttpRequest.Builder connection);
}
