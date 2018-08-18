package main.java.com.tsystems.superrailroad.model.excep;

public class RideException extends RuntimeException {
    public RideException() {
    }

    public RideException(String message) {
        super(message);
    }
}
