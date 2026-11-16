package lab.apiguardian;

import javax.swing.SwingUtilities;
import lab.apiguardian.controller.ApiGuardianController;
import lab.apiguardian.model.RateLimiter;
import lab.apiguardian.view.GuiView;

/**
 * Main entry point for the API Guardian GUI application.
 *
 * @author Jakub
 * @version 2.0
 */
public class ApiGuardian {

    /**
     * Default constructor for ApiGuardian.
     */
    public ApiGuardian() {
    }

    /**
     * Main execution method. Parses optional command-line arguments and launches the Swing GUI.
     * Parameter order:
     * args[0] - Initial request limit (integer, defaults to 3 if omitted)
     * args[1] - Initial client identifier (string, defaults to "192.168.1.1" if omitted)
     *
     * @param args command-line arguments specifying initial request limit and client identifier
     */
    public static void main(String[] args) {
        int initialLimit = (args.length >= 1) ? Integer.parseInt(args[0]) : 3;
        String initialClientIp = (args.length >= 2) ? args[1] : "192.168.1.1";

        SwingUtilities.invokeLater(() -> {
            RateLimiter model = new RateLimiter(initialLimit);
            GuiView view = new GuiView(initialLimit, initialClientIp);
            ApiGuardianController controller = new ApiGuardianController(model, view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}