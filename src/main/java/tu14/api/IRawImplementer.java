package tu14.api;

import tu14.api.exceptions.APITransformException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IRawImplementer<T extends IRawImplementer<T>> {

    T construct(JsonNode data)throws APITransformException;
}
