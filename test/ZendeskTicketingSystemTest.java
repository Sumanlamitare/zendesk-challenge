import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author sumanadhikari
 */
public class ZendeskTicketingSystemTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    public ZendeskTicketingSystemTest() {
    }

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
        System.out.flush();
    }

    @Test
    public void testGetRawData() throws Exception {
        StringBuffer res = ZendeskTicketingSystem.getRawData(0, 1);
        assertEquals(true, res instanceof StringBuffer);
    }

    /**
     * Test of getTickets method, of class ZendeskTicketingSystem.
     */
    @Test
    public void testGetTickets() throws Exception {
        HashMap<String, Boolean> gTickets = ZendeskTicketingSystem.getTickets(0);
        assertEquals(0, gTickets.size());
        assertEquals(true, gTickets instanceof HashMap);
    }

    /**
     * Test of displayTickets method, of class ZendeskTicketingSystem.
     */
    @Test
    public void testDisplayTickets() throws ParseException {
        JSONParser resParser = new JSONParser();
        JSONArray result;
        result = new JSONArray((Collection) resParser.parse("[{\"id\":1,\"created_at\":\"2021-11-19T22:47:16Z\",\"updated_at\":\"2021-11-19T22:47:16Z\",\"type\":\"incident\",\"subject\":\"Sample ticket: Meet the ticket\",\"description\":\"Hi there,\\n\\nI’m sending an email because I’m having a problem setting up your new product. Can you help me troubleshoot?\\n\\nThanks,\\n The Customer\\n\\n\",\"priority\":\"normal\",\"status\":\"open\"},{\"id\":2,\"created_at\":\"2021-11-19T23:28:25Z\",\"updated_at\":\"2021-11-19T23:28:25Z\",\"type\":null,\"subject\":\"velit eiusmod reprehenderit officia cupidatat\",\"description\":\"Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequat sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit incididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaecat ut laborum.\\n\\nAliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillum nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deserunt.\",\"priority\":null,\"status\":\"open\"},{\"id\":3,\"created_at\":\"2021-11-19T23:28:25Z\",\"updated_at\":\"2021-11-19T23:28:25Z\",\"type\":null,\"subject\":\"excepteur laborum ex occaecat Lorem\",\"description\":\"Exercitation amet in laborum minim. Nulla et veniam laboris dolore fugiat aliqua et sit mollit. Dolor proident nulla mollit culpa in officia pariatur officia magna eu commodo duis.\\n\\nAliqua reprehenderit aute qui voluptate dolor deserunt enim aute tempor ad dolor fugiat. Mollit aliquip elit aliqua eiusmod. Ex et anim non exercitation consequat elit dolore excepteur. Aliqua reprehenderit non culpa sit consequat cupidatat elit.\",\"priority\":null,\"status\":\"open\"},{\"id\":4,\"created_at\":\"2021-11-19T23:28:26Z\",\"updated_at\":\"2021-11-19T23:28:26Z\",\"type\":null,\"subject\":\"ad sunt qui aute ullamco\",\"description\":\"Sunt incididunt officia proident elit anim ullamco reprehenderit ut. Aliqua sint amet aliquip cillum minim magna consequat excepteur fugiat exercitation est exercitation. Adipisicing occaecat nisi aliqua exercitation.\\n\\nAute Lorem aute tempor sunt mollit dolor in consequat non cillum irure reprehenderit. Nulla deserunt qui aliquip officia duis incididunt et est velit nulla irure in fugiat in. Deserunt proident est in dolore culpa mollit exercitation ea anim consequat incididunt. Mollit et occaecat ullamco ut id incididunt laboris occaecat qui.\",\"priority\":null,\"status\":\"open\"}]"));
        ZendeskTicketingSystem.displayTickets(result);
        assertThat(outputStreamCaptor.toString(),containsString("List of Tickets"));
    }

}
