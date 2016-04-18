package com.waze;


import com.fasterxml.jackson.databind.JsonNode;
import com.waze.domain.WazeRouteResponse;
import com.waze.domain.WazeRouteWithDirectionsResponse;
import com.waze.domain.WazeStreetPickerResult;
import com.waze.domain.WazeTrafficNotificationsResponse;
import com.waze.exceptions.WazeException;
import com.waze.service.WazeNotificationService;
import com.waze.service.WazeRouteService;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Nimrod_Lahav on 5/22/15.
 */
public class WazeRouteServiceTest {

    WazeRouteService wazeRouteService = new WazeRouteService();
    WazeNotificationService wazeNotificationService = new WazeNotificationService();

    @Test
    @Ignore
    public void getNotificationTest(){
        WazeTrafficNotificationsResponse res = wazeNotificationService.getNotifications("34.560293197631836", "35.08736228942871", "32.126451488404435", "31.967679380737046");
        assert(res.getAlerts().size() >= 1);
        assert(res.getJams().size() >= 1);
    }

    @Test
    @Ignore
    public void testWazeRouteWithDirection(){
        WazeRouteWithDirectionsResponse res = wazeRouteService.getRouteWithParts("40.7794034", "-73.9525884", "40.7866205", "-73.9773711", null, null);
        assert(res.getRoutesWithDirections().size() >= 1);
        assert(res.getRoutesWithDirections().get(0).getRouteStreets().size() > 0);
        assert(res.getRoutesWithDirections().get(0).getRouteParts().size() > 0);
    }

    @Test
    @Ignore
    public void testWazeRoutesWithDirectionText(){
        WazeRouteWithDirectionsResponse res = wazeRouteService.getRouteWithParts("1712 Kilbourne Pl NW, Washington, DC 20010", "2150 K St NW, Washington, DC 20427");
        assert(res.getRoutesWithDirections().size() >= 1);
        assert(res.getRoutesWithDirections().get(0).getRouteStreets().size() > 0);
        assert(res.getRoutesWithDirections().get(0).getRouteParts().size() > 0);
    }


    @Test
    @Ignore
    public void testWazeGetAddressApi(){
        JsonNode addressResult = wazeRouteService.getAddress("5th avenue new york", true);
        String addressResultStr = addressResult.get("name").asText();
        assert(addressResultStr.contains("5th"));
    }

    @Test
    @Ignore
    public void testWazeRouteApi(){
        List<JsonNode> routeResult = wazeRouteService.sendRouteRequest("https://www.waze.com/RoutingManager/routingRequest?from=x%3A-73.96537017822266+y%3A40.77473068237305&to=x%3A-73.99018859863281+y%3A40.751678466796875&at=0&returnJSON=true&returnGeometries=true&returnInstructions=true&timeout=60000&nPaths=3&options=AVOID_TRAILS%3At");
        String routeName = routeResult.get(0).get("response").get("routeName").asText();
        assert(!routeName.equals(""));
    }

    @Test
    @Ignore
    public void testWazeGetRoutesRequest(){
        WazeRouteResponse routeResult = wazeRouteService.getRoutes("5th Avenue, Manhattan, New York, NY, United States", "Queens Blvd, Queens, NY, United States");
        assert(routeResult.getStartPoint().contains("Avenue"));
        assert(routeResult.getEndPoint().contains("Queens"));
    }

    @Test(expected=WazeException.class)
    @Ignore
    public void testWazeGetRoutesRequestReturnErrorWhenUserRouteToLong(){
        wazeRouteService.getRoutes("5th Avenue, Manhattan, New York, NY, United States", "3722 Crenshaw Blvd Los Angeles");
    }

    @Test
    @Ignore
    public void testWazeGetRoutesReturnSingleRequestFromUSA(){
        WazeRouteResponse wazeRouteResponse = wazeRouteService.getRoutes("153 5th Ave, Manhattan, NY, United States", "123 5th Avenue, New York, NY 10003");
        assert(wazeRouteResponse.getRoutes().size() == 1);
        assert(!wazeRouteResponse.getRoutes().get(0).isToll());
        assert(wazeRouteResponse.getRoutes().get(0).getRouteLengthKM() > 0);
        assert(wazeRouteResponse.getRoutes().get(0).getRouteLengthMiles() > 0);
    }

    @Test
    @Ignore
    public void testWazeGetRoutesRequestFromUSA(){
        WazeRouteResponse wazeRouteResponse = wazeRouteService.getRoutes("5th Avenue, Manhattan, New York, NY, United States", "2th Avenue, Manhattan, New York, NY");
        assert(wazeRouteResponse.getRoutes().size() >= 1);
    }

    @Test
    @Ignore
    public void testWazeGetRoutesRequestFromUK(){
        WazeRouteResponse wazeRouteResponse = wazeRouteService.getRoutes("305-307 Green Lanes London, United Kingdom", "360 Green Lanes London, United Kingdom");
        assert(wazeRouteResponse.getRoutes().size() == 2);
    }

    @Test
    @Ignore
    public void testWazeGetRoutesRequestFromIsrael(){
        WazeRouteResponse wazeRouteResponse = wazeRouteService.getRoutes("הירקון 28 תל אביב", "פתח תקווה");
        assert(wazeRouteResponse.getRoutes().size() == 2);
    }

    @Test
    @Ignore
    public void testWazeFreeTextToAddress(){
        WazeStreetPickerResult wazeStreetPickerResult = wazeRouteService.getAddressOptionsFromFreeText("5th Avenue, Manhattan, New York, NY");
        assert(wazeStreetPickerResult.getAddressList().size() >= 1);
        assert(wazeStreetPickerResult.getAddressList().get(0).contains("New York"));
    }

    @Test
    @Ignore
    public void testWazeFreeLatLon(){
        WazeRouteResponse wazeRouteResponse = wazeRouteService.getRoutes("32.07120132446289", "34.7652473449707", "32.06473159790039", "34.86988830566406", null, null);
        assert(wazeRouteResponse.getRoutes().size() == 2);
    }


}