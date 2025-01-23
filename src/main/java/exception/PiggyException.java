package exception;

public class PiggyException extends Exception {
    public PiggyException(String message) {
        super(message); // Pass error message to Exception class
    }
}