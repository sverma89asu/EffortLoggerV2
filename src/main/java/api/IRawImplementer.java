package api;

import api.exceptions.APITransformException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IRawImplementer<T extends IRawImplementer<T>> {

    T construct(JsonNode data)throws APITransformException;
}
