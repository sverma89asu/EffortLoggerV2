package tu14.api.request;

import java.net.http.HttpRequest;

public final class CreateRequest extends APIRequest {
    @Override
    public void includeRequestDataLayer(HttpRequest.Builder request) {
        if (body == null) {
            throw new IllegalStateException("No request body");
        }

        if (id != -1)
            request.header("X-Data-ID", String.valueOf(id));

        request.header("Content-Type", "application/json");
        request.PUT(HttpRequest.BodyPublishers.ofString(body));
    }
}
