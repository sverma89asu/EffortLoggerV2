package api.exceptions;

public class APITransformException extends Exception {

    public APITransformException(Exception e) {
        super(e);
    }

    public APITransformException(String message) {
        super(message);
    }
}
