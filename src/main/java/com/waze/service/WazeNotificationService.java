package com.waze.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.waze.domain.WazeAlert;
import com.waze.domain.WazeJam;
import com.waze.domain.WazeTrafficNotificationsResponse;
import com.waze.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nimrod_Lahav on 5/21/15.
 */
public class WazeNotificationService {

    ObjectMapper mapper = new ObjectMapper();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private static String genNotificationUrl(String server, String left, String right, String top, String bottom){
        return "https://www.waze.com/" + server + "/web/TGeoRSS?left="
                + left + "&right=" + right + "&bottom=" + bottom + "&top=" + top;
    }

    public WazeNotificationService() {}

    //Longitude X-left  Longitude X-right latitude Y-top, latitude Y-bottom
    public WazeTrafficNotificationsResponse getNotifications(String left, String right, String top, String bottom){

        String[] serverList = {"rtserver", "row-rtserver", "il-rtserver"};
        String url = "";
        WazeTrafficNotificationsResponse wazeTrafficNotificationsResponse = new WazeTrafficNotificationsResponse();
        List<WazeAlert> alerts = new ArrayList<>();
        List<WazeJam> jams = new ArrayList<>();


        try{
            for (String server: serverList){
                url = genNotificationUrl(server, left, right, top, bottom);
                String responseStr = asyncHttpClient
                        .prepareGet(url)
                        .execute()
                        .get()
                        .getResponseBody();

                if (!responseStr.isEmpty()){
                    JsonNode result = mapper.readTree(responseStr);
                    if (result.has("alerts")){
                        JsonNode alertsNode = result.get("alerts");
                        for (JsonNode alert: alertsNode){
                            String country = Utils.getStringOrNull("country", alert);
                            int numOfThumbsUp = Utils.getIntOrNull("nThumbsUp", alert);
                            String type = Utils.getStringOrNull("type", alert);
                            String subType = Utils.getStringOrNull("subtype", alert);
                            String placeNearBy = Utils.getStringOrNull("nearBy", alert);
                            String latitude = Utils.getX2StringOrNull("location", "y", alert);
                            String longitude = Utils.getX2StringOrNull("location", "x", alert);
                            WazeAlert wazeAlert = new WazeAlert(country, numOfThumbsUp, type, subType, placeNearBy, latitude, longitude);
                            alerts.add(wazeAlert);
                        }
                    }
                    if (result.has("jams")){
                        for (JsonNode jam: result.get("jams")) {
                            int severity = Utils.getIntOrNull("severity", jam);
                            String type = Utils.getStringOrNull("type", jam);
                            String country = Utils.getStringOrNull("country", jam);
                            String end = Utils.getStringOrNull("endNode", jam);
                            String start = Utils.getStringOrNull("startNode", jam);
                            String street = Utils.getStringOrNull("street", jam);
                            JsonNode lines = jam.get("line");
                            String startLatitude = Utils.getStringOrNull("y", lines.get(0));
                            String startLongitude = Utils.getStringOrNull("x", lines.get(0));
                            String endLatitude = Utils.getStringOrNull("y", lines.get(lines.size() - 1));
                            String endLongitude =  Utils.getStringOrNull("x", lines.get(lines.size() - 1));
                            int delayInSec = Utils.getIntOrNull("delay", jam);
                            WazeJam wazeJam = new WazeJam(severity, type, country, end, start, street, startLatitude, startLongitude, endLatitude, endLongitude, delayInSec);
                            jams.add(wazeJam);
                        }
                    }
                    }
            }
        }catch (Exception ex) {
            throw new RuntimeException("failed to get notifications \nurl: "+ url + "\nerror: "+ ex.getMessage());
        }

        wazeTrafficNotificationsResponse.setAlerts(alerts);
        wazeTrafficNotificationsResponse.setJams(jams);
        return wazeTrafficNotificationsResponse;
    }

}
