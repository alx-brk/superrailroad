package main.java.com.tsystems.superrailroad.model.excep;

public class PassengerExistException extends Exception {
    public PassengerExistException() {
    }

    public PassengerExistException(String message) {
        super(message);
    }
}
