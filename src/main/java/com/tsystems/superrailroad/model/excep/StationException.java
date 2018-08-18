package main.java.com.tsystems.superrailroad.model.excep;

public class StationException extends RuntimeException {
    public StationException() {
    }

    public StationException(String message) {
        super(message);
    }
}
