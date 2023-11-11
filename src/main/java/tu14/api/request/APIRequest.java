package tu14.api.request;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tu14.api.IRawImplementer;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.net.http.HttpRequest;

public abstract sealed class APIRequest permits CreateRequest, DeleteRequest, GetRequest,
        UpdateRequest {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.PROTECTED_AND_PUBLIC);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    protected long id = -1;
    protected String body = null;
    protected String endpoint = null;

    /**
     * Set the Database table to access data from. Known values are listed in Tables.java
     */
    public APIRequest table(String table) {
        endpoint = "/tables/" + table;

        return this;
    }

    /**
     * Set the row ID for requests. Required on Updates and Deletes
     */
    public APIRequest id(long id) {
        if (id < 0) throw new IllegalArgumentException();
        this.id = id;

        return this;
    }

    /**
     * Set the body content for requests. Required on Creates and Updates, prohibited
     * on Gets and Deletes
     */
    public APIRequest body(IRawImplementer<?> implementer) {

        try {
            body = mapper.writeValueAsString(implementer);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return this;
    }

    /**
     * Get the endpoint this request expects to access at the API
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Modify request to include APIRequest data
     */
    public abstract void includeRequestDataLayer(HttpRequest.Builder request);
}
