package api.request;

import java.net.http.HttpRequest;

public class UpdateRequest extends APIRequest {
    @Override
    public void includeRequestDataLayer(HttpRequest.Builder request) {
        if (body == null) {
            throw new IllegalStateException("No request body");
        }

        if (id == -1) {
            throw new IllegalStateException("Delete request must include id");
        }

        request.header("X-Data-ID", String.valueOf(id));
        request.header("Content-Type", "application/json");
        request.POST(HttpRequest.BodyPublishers.ofString(body));
    }
}
