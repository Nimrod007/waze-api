package com.waze.domain;

import java.util.List;

public class WazeTrafficNotificationsResponse {

    private List<WazeAlert> alerts;
    private List<WazeJam> jams;

    public List<WazeAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<WazeAlert> alerts) {
        this.alerts = alerts;
    }

    public List<WazeJam> getJams() {
        return jams;
    }

    public void setJams(List<WazeJam> jams) {
        this.jams = jams;
    }

}

