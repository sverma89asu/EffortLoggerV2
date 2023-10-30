package tu14.api.request;

import java.net.http.HttpRequest;

public final class GetRequest extends APIRequest {
    @Override
    public void includeRequestDataLayer(HttpRequest.Builder request) {
        if (body != null) {
            throw new IllegalStateException("Request body should not exist");
        }

        if (id != -1)
            request.header("X-Data-ID", String.valueOf(id));

        request.GET();
    }
}
