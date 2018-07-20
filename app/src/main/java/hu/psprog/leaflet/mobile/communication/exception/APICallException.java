package hu.psprog.leaflet.mobile.communication.exception;

/**
 * @author Peter Smith
 */
public class APICallException extends RuntimeException {

    public APICallException(String message) {
        super(message);
    }

    public APICallException(String message, Throwable cause) {
        super(message, cause);
    }
}
