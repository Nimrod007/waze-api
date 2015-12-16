package com.waze;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.waze.domain.WazeRouteWithDirectionsResponse;
import com.waze.domain.WazeTrafficNotificationsResponse;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

public class ServerTest {


    @ClassRule
    public static final DropwizardAppRule<WazeConfig> RULE =
            new DropwizardAppRule<>(WazeApp.class, ResourceHelpers.resourceFilePath("conf.yml"));

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testRouteByXY(){
        String endLat = "40.762966999999996";
        String endLon = "-73.973968000000";
        String startLat = "40.76267793274429";
        String startLon = "-73.97328385997065";

        WazeRouteWithDirectionsResponse wazeRouteWithDirectionsResponse = null;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        try {
            Response response = asyncHttpClient.prepareGet("http://localhost:"
                    + RULE.getLocalPort() +
                    "/waze/routesXYwithDirections?endLat=" + endLat + "&endLon=" + endLon + "&startLat=" + startLat + "&startLon=" + startLon)
                    .addHeader("Accept", "application/json").execute().get();

            String responseStr = response.getResponseBody();
            wazeRouteWithDirectionsResponse = mapper.readValue(responseStr, WazeRouteWithDirectionsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncHttpClient.close();
        }

        assert (wazeRouteWithDirectionsResponse != null);
    }

    @Test
    public void testNotifications(){
        String latBottom = "40.72892879374242";
        String latTop = "40.733897887617324";
        String lonLeft = "-74.0093457698822";
        String lonRight = "-73.98462653160095";

        WazeTrafficNotificationsResponse wazeTrafficNotificationsResponse = null;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        try {
            Response response = asyncHttpClient.prepareGet("http://localhost:"
                    + RULE.getLocalPort() +
                    "/waze/traffic-notifications?latBottom=" + latBottom + "&latTop=" + latTop + "&lonLeft=" + lonLeft + "&lonRight=" + lonRight)
                    .addHeader("Accept", "application/json").execute().get();

            String responseStr = response.getResponseBody();
            wazeTrafficNotificationsResponse = mapper.readValue(responseStr, WazeTrafficNotificationsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncHttpClient.close();
        }

        assert (wazeTrafficNotificationsResponse != null);
    }

    @Test
    public void testRouteByText(){
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        String start = "1712 Kilbourne Pl NW, Washington, DC 20010";
        String end = "2150 K St NW, Washington, DC 20427";

        WazeRouteWithDirectionsResponse wazeRouteWithDirectionsResponse = null;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        try {
            Response response = asyncHttpClient.prepareGet("http://localhost:"
                    + RULE.getLocalPort() +
                    "/waze/routesWithDirections?end=" + end + "&start=" + start)
                    .addHeader("Accept", "application/json").execute().get();

            String responseStr = response.getResponseBody();
            wazeRouteWithDirectionsResponse = mapper.readValue(responseStr, WazeRouteWithDirectionsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncHttpClient.close();
        }

        assert (wazeRouteWithDirectionsResponse != null);
    }
}
