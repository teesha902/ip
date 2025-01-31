package exception;


/**
 * Represents a custom exception specific to the PiggyPlanner application.
 * This exception is thrown when an error occurs due to invalid user input
 * or any other domain-specific issues within the application.
 */
public class PiggyException extends Exception {
    /**
     * Constructs a new PiggyException with the specified detail message.
     *
     * @param message The error message describing the exception.
     */
    public PiggyException(String message) {
        super(message); // Pass error message to Exception class
    }
}