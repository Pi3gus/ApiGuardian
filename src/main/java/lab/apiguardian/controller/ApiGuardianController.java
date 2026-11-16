package lab.apiguardian.controller;

import lab.apiguardian.model.RateLimitExceededException;
import lab.apiguardian.model.RateLimiter;
import lab.apiguardian.view.GuiView;

/**
 * Controller coordinating request validation between RateLimiter and GuiView.
 *
 * @author Jakub
 * @version 2.0
 */
public class ApiGuardianController {

    /**
     * Reference to the rate limiting domain model.
     */
    private RateLimiter model;

    /**
     * Reference to the Swing graphical view.
     */
    private final GuiView view;

    /**
     * Constructs a controller instance with required MVC dependencies.
     *
     * @param model domain model instance
     * @param view Swing presentation view instance
     */
    public ApiGuardianController(RateLimiter model, GuiView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Resets the domain model with a new request limit.
     *
     * @param newLimit new maximum number of allowed requests
     */
    public void resetLimiter(int newLimit) {
        this.model = new RateLimiter(newLimit);
        view.displayResetInfo(newLimit);
    }

    /**
     * Processes an incoming client request and updates the GUI view.
     *
     * @param clientIdentifier IP address or identifier of the requesting client
     */
    public void handleRequest(String clientIdentifier) {
        try {
            model.processIncomingRequest();
            view.displaySuccess(
                    clientIdentifier,
                    model.getRemainingRequests(),
                    model.getRequestLimit()
            );
        } catch (RateLimitExceededException exception) {
            view.displayBlocked(clientIdentifier, exception.getMessage());
        }
    }
}