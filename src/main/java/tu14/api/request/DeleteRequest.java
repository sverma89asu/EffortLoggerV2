package tu14.api.request;

import java.net.http.HttpRequest;

public class DeleteRequest extends APIRequest {
    @Override
    public void includeRequestDataLayer(HttpRequest.Builder request) {
        if (body != null) {
            throw new IllegalStateException("Request body should not exist");
        }

        if (id == -1) {
            throw new IllegalStateException("Delete request must include id");
        }

        request.header("X-Data-ID", String.valueOf(id));
        request.DELETE();
    }
}
