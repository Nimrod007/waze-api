package com.waze.domain;

import java.util.ArrayList;

public class WazeRouteDirection {

    private String routeName;
    private int routeDurationInMinutes;
    private double routeLengthKM;
    private boolean toll;
    private ArrayList<WazeRoutePart> routeParts;
    private ArrayList<String> routeStreets;

    public WazeRouteDirection(){}

    public WazeRouteDirection(String routeName, int routeDurationInMinutes, double routeLengthKM, boolean toll, ArrayList<WazeRoutePart> routeParts, ArrayList<String> routeStreets) {
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

    public ArrayList<WazeRoutePart> getRouteParts() {
        return routeParts;
    }

    public ArrayList<String> getRouteStreets() {
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
