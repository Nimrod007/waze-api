package com.waze.domain;

import java.util.List;

public class WazeRouteDirection {

    private String routeName;
    private int routeDurationInMinutes;
    private double routeLengthKM;
    private boolean toll;
    private List<WazeRoutePart> routeParts;
    private List<String> routeStreets;

    public WazeRouteDirection(){}

    public WazeRouteDirection(String routeName, int routeDurationInMinutes, double routeLengthKM, boolean toll, List<WazeRoutePart> routeParts, List<String> routeStreets) {
        this.routeName = routeName;
        this.routeDurationInMinutes = routeDurationInMinutes;
        this.routeLengthKM = routeLengthKM;
        this.toll = toll;
        this.routeParts = routeParts;
        this.routeStreets = routeStreets;
    }

    public String getRouteName() {
        return routeName;
    }

    public List<WazeRoutePart> getRouteParts() {
        return routeParts;
    }

    public List<String> getRouteStreets() {
        return routeStreets;
    }

    public int getRouteDurationInMinutes() {
        return routeDurationInMinutes;
    }

    public double getRouteLengthKM() {
        return routeLengthKM;
    }

    public boolean isToll() {
        return toll;
    }
}
