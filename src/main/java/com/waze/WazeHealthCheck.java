package com.waze;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.JsonNode;
import com.waze.service.WazeRouteService;

public class WazeHealthCheck extends HealthCheck {

    private final WazeRouteService wazeRouteService;

    public WazeHealthCheck(WazeRouteService wazeRouteService) {
        this.wazeRouteService = wazeRouteService;
    }

    @Override
    protected Result check() throws Exception {
        JsonNode addressResult = wazeRouteService.getAddress("5th avenue new york", true);
        String addressResultStr = addressResult.get("name").asText();
        if (!addressResultStr.contains("5th")) {
            return Result.unhealthy("waze api failed to query waze web site");
        }
        return Result.healthy();
    }

}
