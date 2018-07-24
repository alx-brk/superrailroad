package main.java.com.tsystems.superrailroad.model.excep;

public class CreateRouteException extends RuntimeException {
    public CreateRouteException() {
    }

    public CreateRouteException(String message) {
        super(message);
    }
}
