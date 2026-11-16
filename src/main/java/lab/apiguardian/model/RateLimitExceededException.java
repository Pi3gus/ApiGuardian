package lab.apiguardian.model;

/**
 * Custom checked exception thrown when an API request exceeds the configured threshold.
 *
 * @author Jakub
 * @version 1.0
 */
public class RateLimitExceededException extends Exception {

    /**
     * Constructs a new exception instance with the specified error message.
     *
     * @param message detailed description of the limit violation
     */
    public RateLimitExceededException(String message) {
        super(message);
    }
}