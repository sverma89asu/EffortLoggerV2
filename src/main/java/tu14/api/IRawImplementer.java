package tu14.api;

import tu14.api.exceptions.APITransformException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IRawImplementer<T extends IRawImplementer<T>> {

    /**
     * Transform JSON data into objects. Meant to be called by Backend
     */
    T construct(JsonNode data)throws APITransformException;
}
