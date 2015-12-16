package com.waze.domain;

import java.util.ArrayList;

public class WazeTrafficNotificationsResponse {

    public ArrayList<WazeAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<WazeAlert> alerts) {
        this.alerts = alerts;
    }

    public ArrayList<WazeJam> getJams() {
        return jams;
    }

    public void setJams(ArrayList<WazeJam> jams) {
        this.jams = jams;
    }

    private ArrayList<WazeAlert> alerts;
    private ArrayList<WazeJam> jams;


}

