package main.java.com.tsystems.superrailroad.model.excep;

public class RouteException extends RuntimeException {
    public RouteException() {
    }

    public RouteException(String message) {
        super(message);
    }
}
