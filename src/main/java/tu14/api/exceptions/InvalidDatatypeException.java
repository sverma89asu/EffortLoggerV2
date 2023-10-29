package tu14.api.exceptions;

public class InvalidDatatypeException extends APITransformException {
    public InvalidDatatypeException(Exception e) {
        super(e);
    }

    public InvalidDatatypeException(String e) {
        super(e);
    }
}
