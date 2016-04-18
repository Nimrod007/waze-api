package com.waze.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.waze.domain.*;
import com.waze.exceptions.WazeException;
import com.waze.utils.Utils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nimrod_Lahav on 5/21/15.
 */
public class WazeRouteService {
    private static final String LOCATION = "location";
    private static final String RESPONSE = "response";
    private static final String IS_TOLL = "isToll";
    private static final String CROSS_TIME = "crossTime";
    private static final String LENGTH = "length";
    private static final String COORDS = "coords";

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();


    boolean singleAddress = true;
    boolean multipleAddress = false;
    ObjectMapper mapper = new ObjectMapper();

    public WazeRouteService() {}

    public WazeStreetPickerResult getAddressOptionsFromFreeText(String addressText){
        JsonNode results = getAddress(addressText, multipleAddress);

        List<String> addressList = new ArrayList<>();
        for (JsonNode address: results){
            addressList.add(Utils.getStringOrNull("name", address));
        }

        if (addressList.size() < 10) //TODO move size limit to conf file
            return new WazeStreetPickerResult(addressList);
        else
            return new WazeStreetPickerResult(addressList.subList(0, 10));
    }

    private List<JsonNode> sendRouteRequestsWithFallback(String startLat, String startLon, String endLat, String endLon){

        String[] serverList = {"RoutingManager", "row-RoutingManager", "il-RoutingManager"}; //TODO server list to conf file

        List<JsonNode> routeResponse = new ArrayList<>();

        for (String server: serverList){
            String routeURL = createWazeRouteURL(server, startLat, startLon, endLat, endLon);
            routeResponse = sendRouteRequest(routeURL);
            if (!routeResponse.isEmpty() && !routeResponse.get(0).has("error")){
                return routeResponse;
            }
        }

        if (routeResponse.isEmpty()) {
            throw new WazeException("route response is empty");
        }
        else {
            String wazeErrMsg = routeResponse.get(0).get("error").asText();
            throw new WazeException(wazeErrMsg);
        }
    }

    public WazeRouteWithDirectionsResponse getRouteWithParts(String start, String end) {

        JsonNode startNode = getAddress(start, singleAddress);
        JsonNode endNode = getAddress(end, singleAddress);

        String startLat = Utils.getX2StringOrNull(LOCATION, "lat", startNode);
        String startLon = Utils.getX2StringOrNull(LOCATION, "lon", startNode);

        String endLat = Utils.getX2StringOrNull(LOCATION, "lat", endNode);
        String endLon = Utils.getX2StringOrNull(LOCATION, "lon", endNode);

        String startName = Utils.getStringOrNull("name", startNode);
        String endName = Utils.getStringOrNull("name", endNode);

        return getRouteWithParts(startLat, startLon, endLat, endLon, startName, endName);
    }

    public WazeRouteWithDirectionsResponse getRouteWithParts(String startLat, String startLon, String endLat, String endLon,String start , String end) {

        List<WazeRouteDirection> wazeRouteDirections = new ArrayList<>();

        List<JsonNode> routeResponse = sendRouteRequestsWithFallback(startLat, startLon, endLat, endLon);

        if (routeResponse.isEmpty()){
            return null;
        }

        String startPoint = start != null ? start : startLat + " " + startLon;
        String endPoint = end != null ? end : endLat + " " + endLon;

        for (JsonNode routeNode : routeResponse) {
            boolean isToll = false;
            int routeDuration = 0;
            int routeLengthMeter = 0;

            String routeName = Utils.getX2StringOrNull(RESPONSE, "routeName", routeNode);
            List<String> streetList = new ArrayList<>();

            JsonNode streets = routeNode.get(RESPONSE).get("streetNames");

            for (JsonNode street : streets) {
                streetList.add(street.asText());
            }

            List<WazeRoutePart> wazeRouteParts = new ArrayList<>();
            JsonNode routeParts = routeNode.get(RESPONSE).get("results");
            for (JsonNode part : routeParts) {

                if(part.get(IS_TOLL).asBoolean()){
                    isToll = true;
                }
                routeDuration += Utils.getIntOrNull(CROSS_TIME, part);
                routeLengthMeter += Utils.getIntOrNull(LENGTH, part);

                String latitude = Utils.getX2StringOrNull("path", "y", part); //Y
                String longitude = Utils.getX2StringOrNull("path", "x", part); //X
                String instruction = Utils.getX2StringOrNull("instruction", "opcode", part);
                int nameIndex = Utils.getIntOrNull("street", part);
                String streetName = streetList.get(nameIndex);
                Boolean tollRoad = part.get(IS_TOLL).asBoolean();
                int timeToCross = Utils.getIntOrNull(CROSS_TIME, part);
                int timeFromStart = Utils.getIntOrNull("distance", part);
                int lengthOfPart = Utils.getIntOrNull(LENGTH, part);

                WazeRoutePart wazeRoutePart = new WazeRoutePart(latitude, longitude, instruction, streetName, tollRoad, timeToCross, timeFromStart, lengthOfPart);
                wazeRouteParts.add(wazeRoutePart);
            }

            int routeDurationMinutes = routeDuration / 60;
            double routeLengthKM = routeLengthMeter / 1000d;
            WazeRouteDirection wazeRouteDirection = new WazeRouteDirection(routeName, routeDurationMinutes, routeLengthKM, isToll,  wazeRouteParts, streetList);
            wazeRouteDirections.add(wazeRouteDirection);
        }

        WazeRouteWithDirectionsResponse wazeRouteWithDirectionsResponse = new WazeRouteWithDirectionsResponse(startPoint, endPoint, startLat, startLon, endLat, endLon, wazeRouteDirections);
        return wazeRouteWithDirectionsResponse;
    }

    public WazeRouteResponse getRoutes(String startLat, String startLon, String endLat, String endLon,String start , String end) {

        Iterable<JsonNode> routeResponse = sendRouteRequestsWithFallback(startLat, startLon, endLat, endLon);

        List<WazeRoute> routes = new ArrayList<>();
        String startLatitude = "";
        String startLongitude = "";
        String endLatitude = "";
        String endLongitude = "";
        String startPoint = start != null ? start : startLat + " " + startLon;
        String endPoint = end != null ? end : endLat + " " + endLon;

        for (JsonNode routeNode : routeResponse) {
            boolean isToll = false;
            int routeLengthMeter = 0;
            String routeName = routeNode.get(RESPONSE).get("routeName").asText();
            JsonNode routeParts = routeNode.get(RESPONSE).get("results");

            startLongitude = routeNode.get(COORDS).get(0).get("x").asText();
            startLatitude = routeNode.get(COORDS).get(0).get("y").asText();

            endLongitude = routeNode.get(COORDS).get(routeNode.size()-1).get("x").asText();
            endLatitude = routeNode.get(COORDS).get(routeNode.size()-1).get("y").asText();

            int routeDuration = 0;

            for (JsonNode part : routeParts) {
                routeDuration += part.get(CROSS_TIME).asInt();
                routeLengthMeter += part.get(LENGTH).asInt();
                if(part.get(IS_TOLL).asBoolean()){
                    isToll = true;
                }
            }

            int routeDurationMinutes = routeDuration / 60;
            double routeLengthKM = routeLengthMeter / 1000d;
            double routeLengthMiles = routeLengthMeter * 0.000621371d;

            WazeRoute wazeRoute = new WazeRoute(routeName, routeDurationMinutes, isToll, routeLengthKM, routeLengthMiles);
            routes.add(wazeRoute);
        }

        return new WazeRouteResponse(routes, startPoint, endPoint, startLatitude, startLongitude, endLatitude, endLongitude);
    }

    public WazeRouteResponse getRoutes(String start, String end) {

        JsonNode startNode = getAddress(start, singleAddress);
        JsonNode endNode = getAddress(end, singleAddress);

        String startLat = startNode.get(LOCATION).get("lat").asText();
        String startLon = startNode.get(LOCATION).get("lon").asText();

        String endLat = endNode.get(LOCATION).get("lat").asText();
        String endLon = endNode.get(LOCATION).get("lon").asText();

        return getRoutes(startLat, startLon, endLat, endLon, startNode.get("name").asText(), endNode.get("name").asText());
    }



    public List<JsonNode> sendRouteRequest(String routeUrl){
        List<JsonNode> routes = new ArrayList<>();
        try{
            Response response = asyncHttpClient
                    .prepareGet(routeUrl)
                    .execute()
                    .get();

            String responseStr = response.getResponseBody();//.replaceAll("NaN", "\"NaN\"");
            if (responseStr.isEmpty()) return routes;

            JsonNode result = mapper.readTree(responseStr);
            if (result.has("alternatives")){ //TODO git user option to choose number of routes (conf file and api change)
                JsonNode routesAlternatives = result.get("alternatives");
                routes.add(routesAlternatives.get(0));
                routes.add(routesAlternatives.get(1));
            }else{
                routes.add(result);
            }
        }catch (Exception ex) {
            throw new RuntimeException("failed to query waze route \nurl: " + routeUrl + " \nerror: "+ ex.getMessage());
        }

        return routes;
    }

    public JsonNode getAddress(String address, boolean single){
        String addressURL = "";
        try {
            addressURL = "https://www.waze.com/SearchServer/mozi?q=" + URLEncoder.encode(address, "UTF-8") + "&lang=eng&lon=-73.96888732910156%20&lat=40.799981900731964&origin=livemap";
            Response response = asyncHttpClient
                    .prepareGet(addressURL)
                    .execute().get();
            if (single)
                return mapper.readTree(response.getResponseBody()).get(0);
            else
                return mapper.readTree(response.getResponseBody());

        }catch (Exception ex) {
            throw new WazeException("failed to query waze address \nurl: " + addressURL + " \nerror: "+ ex.getMessage());
        }
    }

    private static String createWazeRouteURL(String wazeServer, String startLat, String startLon, String endLat, String endLon){
        return "https://www.waze.com/" + wazeServer + "/routingRequest?from=x%3A"
                + startLon + "+y%3A" + startLat + "&to=x%3A" + endLon +
                "+y%3A" + endLat + "&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=3&options=AVOID_TRAILS%3At";
    }

}
