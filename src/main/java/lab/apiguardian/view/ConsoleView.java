package lab.apiguardian.view;

/**
 * Presentation view responsible for rendering security audit reports in the console.
 *
 * @author Jakub
 * @version 1.1
 */
/**
* Default constructor for ConsoleView.
*/
public class ConsoleView {

    /**
     * Displays a formatted security report for an accepted request.
     *
     * @param clientIdentifier IP address or unique identifier of the client
     * @param remaining number of remaining allowed requests
     * @param limit maximum defined threshold
     */
    public void printSuccessReport(String clientIdentifier, int remaining, int limit) {
        System.out.println("=================================================");
        System.out.println("        API GUARDIAN - SECURITY AUDIT REPORT     ");
        System.out.println("=================================================");
        System.out.println("Client IP / ID     : " + clientIdentifier);
        System.out.println("Configured limit   : " + limit);
        System.out.println("Remaining requests : " + remaining);
        System.out.println("-------------------------------------------------");
        System.out.println("DECISION: [200 OK] Request accepted.");
        System.out.println("=================================================");
    }

    /**
     * Displays a security alert when a custom exception or limit violation occurs.
     *
     * @param clientIdentifier IP address or unique identifier of the client
     * @param errorMessage detailed violation description
     */
    public void printErrorReport(String clientIdentifier, String errorMessage) {
        System.out.println("=================================================");
        System.out.println("        API GUARDIAN - SECURITY ALERT            ");
        System.out.println("=================================================");
        System.out.println("Client IP / ID     : " + clientIdentifier);
        System.out.println("-------------------------------------------------");
        System.out.println("DECISION: [429 BLOCKED] " + errorMessage);
        System.out.println("=================================================");
    }
}