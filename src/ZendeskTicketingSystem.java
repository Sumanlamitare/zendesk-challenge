
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Suman Lamitare
 */
public class ZendeskTicketingSystem {

    private final static int LIMIT = 25;
    private static String paginatedZenPrevUrl = null;
    private static String paginatedZenNextUrl = null;
    private static int pageNavCounter = 1;

    private static HashMap<Integer, Object> allTickets = new HashMap<>();

    /**
     * This method utilizes the ENV variables passed with the main call
     *
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static StringBuffer getRawData(int dir, int id) throws MalformedURLException, IOException {
        String ZENDESK_URI_WITH_PAGINATION = null;
        String ZENDESK_URI = null;
        if (id == 0) {
            if (paginatedZenPrevUrl == null && paginatedZenNextUrl == null) {

                ZENDESK_URI = System.getenv("ZENDESK_URI");
                ZENDESK_URI_WITH_PAGINATION = ZENDESK_URI + "/api/v2/tickets.json?page[size]=" + LIMIT;

            } else {
                ZENDESK_URI = "not-going-to-use-here";
                if (dir == 4) {
                    ZENDESK_URI_WITH_PAGINATION = paginatedZenPrevUrl;
                } else if (dir == 5) {
                    pageNavCounter += 1;
                    ZENDESK_URI_WITH_PAGINATION = paginatedZenNextUrl;
                }

            }
        } else {
            ZENDESK_URI = System.getenv("ZENDESK_URI");
            ZENDESK_URI_WITH_PAGINATION = ZENDESK_URI + "/api/v2/tickets/" + id;
        }
        String ZENDESK_USER_NAME = System.getenv("ZENDESK_USER_NAME");

        String ZENDESK_TOKEN = System.getenv("ZENDESK_TOKEN");
        StringBuffer result = new StringBuffer();

        if (ZENDESK_URI != null && ZENDESK_TOKEN != null) {
            try {
                //
                String origString = ZENDESK_USER_NAME + "/token:" + ZENDESK_TOKEN;

                String base64EncodedCreds;
                base64EncodedCreds = Base64.getEncoder().encodeToString(origString.getBytes());
                URL zenUrl = new URL(ZENDESK_URI_WITH_PAGINATION);
                HttpURLConnection zenConn = (HttpURLConnection) zenUrl.openConnection();
                zenConn.setRequestMethod("GET");

                zenConn.setRequestProperty("Authorization", "Basic " + base64EncodedCreds);
                zenConn.setRequestProperty("Accept", "application/json");
                zenConn.connect();
                int respCode = zenConn.getResponseCode();
                // Server is having a hard time
                if (respCode >= 500 && respCode < 600) {
                    System.out.println("Looks like the API is unavailable");
                } else if (respCode == 404) {
                    throw new IOException("The ticket number you entered does not exist in our system.");
                } else if (respCode == 401) {
                    throw new IOException("The user name  or password is either empty or invalid");
                } else if (respCode != 200) {

                    throw new IOException("Invalid response from the Zendesk API server");

                }

                BufferedReader in = new BufferedReader(new InputStreamReader(zenConn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    result.append(inputLine);
                }
                in.close();
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        return result;
    }

    /*
    Method to get the All tickets from the API using Hashlist
       - Assuming there less number of tickets for this use case i am loading all the tickets into a hashmap to 
         minimize outgoing API call to zendesk servers and serving the client pagination request within the memory.
         But if the in real life use case this solution defers its implementation. So i would make one API call per page
         and serve real time.
     */
    public static HashMap<String, Boolean> getTickets(int pageDirection) throws IOException, ParseException {
        HashMap<String, Boolean> navigation = new HashMap();
        try {
            JSONParser resParser = new JSONParser();
            JSONObject result = new JSONObject((Map) resParser.parse(getRawData(pageDirection, 0).toString()));
            // @TODO: Read the individual tickets from the JSON Array and put the <id, Object> to the HashMap
            JSONArray tickets = (JSONArray) result.get("tickets");
            JSONObject links = (JSONObject) result.get("links");
            JSONObject meta = (JSONObject) result.get("meta");
            boolean hasMore = (boolean) meta.get("has_more");

            paginatedZenNextUrl = (String) links.get("next");
            paginatedZenPrevUrl = (String) links.get("prev");

            navigation.put("prev", pageNavCounter > 1);
            navigation.put("next", hasMore);
            displayTickets(tickets);
        } catch (Exception e) {
            System.out.println("");
        }
        return navigation;

    }

    public static void displayTickets(JSONArray tickets) {
        System.out.println("************************** List of Tickets ***************************************");
        System.out.println("ID\tStatus\tDate Created\t\tDate Updated\t\tSubject");
        System.out.println("-----------------------------------------------------------------------------------");
        for (int i = 0; i < tickets.size(); i++) {
            JSONObject ticket = (JSONObject) tickets.get(i);
            System.out.println(ticket.get("id") + "\t" + ticket.get("status") + "\t" + ticket.get("created_at") + "\t" + ticket.get("updated_at") + "\t" + ticket.get("subject"));

        }
        System.out.println("************************** End of the list ***************************************\n");
    }

    public static void displayTicketDetail(int id) throws IOException, ParseException {
        try {
            JSONParser resParser = new JSONParser();
            JSONObject result = new JSONObject((Map) resParser.parse(getRawData(0, id).toString()));
            JSONObject ticket = (JSONObject) result.get("ticket");
            System.out.println("*********************************************");
            System.out.println("* The ticket you requested is listed below: *");
            System.out.println("*********************************************");
            System.out.println(" ID: " + ticket.get("id") + "\n Status: " + ticket.get("status") + "\n Subject: " + ticket.get("subject") + "\n Date Created: " + ticket.get("created_at") + "\n Updated: " + ticket.get("updated_at") + "\n Description: " + ticket.get("description"));
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        int choice;
        HashMap<String, Boolean> nav = new HashMap();
        nav.put("prev", false);
        nav.put("next", false);

        while (true) {
            System.out.println("************************************");
            System.out.println("* Welcome to Zendesk Ticket Viewer *");
            System.out.println("************************************");
            String option1 = "1) Get All Tickets \n";
            if (nav.get("next") != null && nav.get("prev") != null) {
                if (nav.get("next") && nav.get("prev")) {
                    option1 = "4) Get previous Tickets  5) Get next Tickets \n";
                } else if (nav.get("next")) {
                    option1 = "5) Get next Tickets \n";
                } else if (nav.get("prev")) {
                    option1 = "4) Get prev Tickets \n";
                }
            }

            String prompt = "What would you like to do today? \n" + option1 + "2) Get one ticket \n3) Exit";
            System.out.println(prompt);
            choice = scan.nextInt();
            try {
                switch (choice) {
                    case 1:
                    case 4:
                    case 5:

                        nav = getTickets(choice);

                        break;
                    case 2: {
                        //display one ticket
                        int ticketNumber;
                        System.out.println("Please enter the ticket Number: ");
                        ticketNumber = scan.nextInt();
                        displayTicketDetail(ticketNumber);
                    }
                    break;
                    default:
                        System.exit(0);
                }

            } catch (IOException | ParseException e) {
                System.out.println(e.getMessage());
            }

        }

    }

}
