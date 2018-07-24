package main.java.com.tsystems.superrailroad.model.excep;

public class CreateRideException extends RuntimeException {
    public CreateRideException() {
    }

    public CreateRideException(String message) {
        super(message);
    }
}
