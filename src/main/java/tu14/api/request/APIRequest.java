package tu14.api.request;

import tu14.api.IRawImplementer;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.net.http.HttpRequest;

public abstract class APIRequest {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
    }

    protected long id = -1;
    protected String body = null;
    protected String endpoint = null;

    public APIRequest table(String table) {
        endpoint = "/tables/" + table;

        return this;
    }

    public APIRequest id(long id) {
        if (id <= 0) throw new IllegalArgumentException();
        this.id = id;

        return this;
    }

    public APIRequest body(IRawImplementer implementer) {
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        try {
            body = mapper.writeValueAsString(implementer);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public abstract void includeRequestDataLayer(HttpRequest.Builder request);
}
