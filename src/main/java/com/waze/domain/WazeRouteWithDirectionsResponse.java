package com.waze.domain;

import java.util.List;

public class WazeRouteWithDirectionsResponse {

    private String startPoint;
    private String endPoint;
    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;
    private List<WazeRouteDirection> routesWithDirections;

    public WazeRouteWithDirectionsResponse(){}

    public WazeRouteWithDirectionsResponse(String startPoint, String endPoint, String startLatitude, String startLongitude, String endLatitude, String endLongitude, List<WazeRouteDirection> routesWithDirections) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.routesWithDirections = routesWithDirections;
    }


    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public List<WazeRouteDirection> getRoutesWithDirections() {
        return routesWithDirections;
    }

}
