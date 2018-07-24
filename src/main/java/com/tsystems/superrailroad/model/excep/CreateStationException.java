package main.java.com.tsystems.superrailroad.model.excep;

public class CreateStationException extends RuntimeException {
    public CreateStationException() {
    }

    public CreateStationException(String message) {
        super(message);
    }
}
