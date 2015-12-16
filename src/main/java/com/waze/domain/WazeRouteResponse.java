package com.waze.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Nimrod_Lahav on 5/23/15.
 */
public class WazeRouteResponse {

    private ArrayList<WazeRoute> routes;
    private String startPoint;
    private String endPoint;
    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;

    public WazeRouteResponse() {
    }

    public WazeRouteResponse(ArrayList<WazeRoute> routes, String startPoint, String endPoint, String startLatitude, String startLongitude, String endLatitude, String endLongitude) {
        this.routes = routes;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }


    @JsonProperty
    public ArrayList<WazeRoute> getRoutes() {
        return routes;
    }

    @JsonProperty
    public String getStartPoint() {
        return startPoint;
    }

    @JsonProperty
    public String getEndPoint() {
        return endPoint;
    }

    @JsonProperty
    public String getStartLatitude() {
        return startLatitude;
    }

    @JsonProperty
    public String getStartLongitude() {
        return startLongitude;
    }

    @JsonProperty
    public String getEndLatitude() {
        return endLatitude;
    }

    @JsonProperty
    public String getEndLongitude() {
        return endLongitude;
    }
}
