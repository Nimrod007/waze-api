package com.waze.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Nimrod_Lahav on 5/22/15.
 */
public class WazeRoute {

    private String routeName;
    private int routeDurationInMinutes;
    private double routeLengthKM;
    private double routeLengthMiles;
    private boolean isToll;

    public WazeRoute(){}

    public WazeRoute(String routeName, int routeDurationInMinutes, boolean isToll, double routeLengthKM, double routeLengthMiles) {
        this.routeName = routeName;
        this.routeDurationInMinutes = routeDurationInMinutes;
        this.isToll = isToll;
        this.routeLengthKM = routeLengthKM;
        this.routeLengthMiles = routeLengthMiles;
    }

    @JsonProperty
    public String getRouteName() {
        return routeName;
    }

    @JsonProperty
    public int getRouteDurationInMinutes() {
        return routeDurationInMinutes;
    }

    @JsonProperty
    public boolean isToll() {return isToll;}

    @JsonProperty
    public double getRouteLengthKM() { return routeLengthKM;}

    @JsonProperty
    public double getRouteLengthMiles() { return routeLengthMiles;}
}
