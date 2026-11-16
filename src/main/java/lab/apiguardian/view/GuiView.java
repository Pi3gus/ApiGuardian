package lab.apiguardian.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lab.apiguardian.controller.ApiGuardianController;

/**
 * Graphical user interface window for the API Guardian application.
 *
 * @author Jakub
 * @version 1.0
 */
public class GuiView extends JFrame {

    /**
     * Text field for entering the maximum request limit.
     */
    private final JTextField limitField;

    /**
     * Text field for entering the client IP address or identifier.
     */
    private final JTextField clientIpField;

    /**
     * Button used to apply a new request limit and reset the limiter.
     */
    private final JButton setLimitButton;

    /**
     * Button used to simulate sending an incoming API request.
     */
    private final JButton sendRequestButton;

    /**
     * Read-only text area displaying security audit logs.
     */
    private final JTextArea outputArea;

    /**
     * Reference to the application controller.
     */
    private ApiGuardianController controller;

    /**
     * Constructs the main application window with controls, layout, and accessibility settings.
     *
     * @param initialLimit initial request limit displayed in the input field
     * @param initialClientIp initial client IP or ID displayed in the input field
     */
    public GuiView(int initialLimit, String initialClientIp) {
        super("API Guardian - Rate Limiter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel controlPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel limitLabel = new JLabel("Request Limit:");
        limitLabel.setDisplayedMnemonic(KeyEvent.VK_L);
        limitField = new JTextField(String.valueOf(initialLimit));
        limitLabel.setLabelFor(limitField);
        limitField.setToolTipText("Enter the maximum number of allowed API requests (Alt+L)");
        limitField.getAccessibleContext().setAccessibleDescription("Text field for entering the maximum request limit");

        JLabel clientIpLabel = new JLabel("Client IP / ID:");
        clientIpLabel.setDisplayedMnemonic(KeyEvent.VK_I);
        clientIpField = new JTextField(initialClientIp);
        clientIpLabel.setLabelFor(clientIpField);
        clientIpField.setToolTipText("Enter the client IP address or identifier (Alt+I)");
        clientIpField.getAccessibleContext().setAccessibleDescription("Text field for entering client IP address or ID");

        setLimitButton = new JButton("Set / Reset Limit");
        setLimitButton.setMnemonic(KeyEvent.VK_R);
        setLimitButton.setToolTipText("Initialize or reset the rate limiter with the specified limit (Alt+R)");
        setLimitButton.getAccessibleContext().setAccessibleDescription("Button to apply a new request limit and reset counter");

        sendRequestButton = new JButton("Send API Request");
        sendRequestButton.setMnemonic(KeyEvent.VK_S);
        sendRequestButton.setToolTipText("Simulate sending an incoming API request from the specified client (Alt+S)");
        sendRequestButton.getAccessibleContext().setAccessibleDescription("Button to send and verify an incoming API request");

        controlPanel.add(limitLabel);
        controlPanel.add(limitField);
        controlPanel.add(clientIpLabel);
        controlPanel.add(clientIpField);
        controlPanel.add(setLimitButton);
        controlPanel.add(sendRequestButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFocusable(false);
        outputArea.setToolTipText("Security audit log showing 200 OK and 429 BLOCKED decisions");
        outputArea.getAccessibleContext().setAccessibleDescription("Read-only text area displaying API Guardian audit results");

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button event listeners
        setLimitButton.addActionListener(e -> {
            if (controller != null) {
                int newLimit = Integer.parseInt(limitField.getText().trim());
                controller.resetLimiter(newLimit);
            }
        });

        sendRequestButton.addActionListener(e -> {
            if (controller != null) {
                String clientIp = clientIpField.getText().trim();
                controller.handleRequest(clientIp);
            }
        });
    }

    /**
     * Attaches the controller to this view.
     *
     * @param controller application controller instance
     */
    public void setController(ApiGuardianController controller) {
        this.controller = controller;
    }

    /**
     * Appends an accepted request report to the GUI output log.
     *
     * @param clientIdentifier client IP or ID
     * @param remaining remaining allowed requests
     * @param limit configured maximum limit
     */
    public void displaySuccess(String clientIdentifier, int remaining, int limit) {
        outputArea.append(String.format(
                "[200 OK] Client: %s | Remaining: %d / %d%n",
                clientIdentifier, remaining, limit
        ));
    }

    /**
     * Appends a blocked request report to the GUI output log.
     *
     * @param clientIdentifier client IP or ID
     * @param message rejection details
     */
    public void displayBlocked(String clientIdentifier, String message) {
        outputArea.append(String.format(
                "[429 BLOCKED] Client: %s | %s%n",
                clientIdentifier, message
        ));
    }

    /**
     * Appends a status message when the limiter is reset.
     *
     * @param newLimit newly configured limit
     */
    public void displayResetInfo(int newLimit) {
        outputArea.append(String.format(
                "--- RateLimiter reset. New limit: %d ---%n",
                newLimit
        ));
    }
}