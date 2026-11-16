package lab.apiguardian.model;

/**
 * Business model maintaining request counters and enforcing API usage quotas.
 *
 * @author Jakub
 * @version 1.1
 */
public class RateLimiter {

    /**
     * The maximum number of allowed requests.
     */
    private final int requestLimit;

    /**
     * The current count of processed incoming requests.
     */
    private int requestCount;

    /**
     * Constructs a new rate limiter with the specified request threshold.
     *
     * @param requestLimit maximum permissible requests
     */
    public RateLimiter(int requestLimit) {
        this.requestLimit = requestLimit;
        this.requestCount = 0;
    }

    /**
     * Validates and registers an incoming request against the defined quota.
     *
     * @throws RateLimitExceededException if the request count reaches or exceeds the limit, or if limit is invalid
     */
    public void processIncomingRequest() throws RateLimitExceededException {
        if (requestLimit <= 0 || requestCount >= requestLimit) {
            throw new RateLimitExceededException(
                    "Request rejected: quota of " + requestLimit + " requests exceeded."
            );
        }
        requestCount++;
    }

    /**
     * Retrieves the configured upper limit of requests.
     *
     * @return maximum allowed request limit
     */
    public int getRequestLimit() {
        return requestLimit;
    }

    /**
     * Retrieves the number of currently recorded requests.
     *
     * @return total processed requests
     */
    public int getRequestCount() {
        return requestCount;
    }

    /**
     * Computes the number of remaining available requests.
     *
     * @return non-negative count of remaining requests
     */
    public int getRemainingRequests() {
        return Math.max(0, requestLimit - requestCount);
    }
}