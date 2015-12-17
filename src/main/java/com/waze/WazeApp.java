package com.waze;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.waze.controller.WazeEndPoint;
import com.waze.service.WazeNotificationService;
import com.waze.service.WazeRouteService;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class WazeApp extends Application<WazeConfig> {

    public static void main(String[] args) throws Exception {

        System.setProperty("file.encoding", "UTF-8");
        new WazeApp().run(args);
    }

    @Override
    public String getName() {
        return "waze-app";
    }

    @Override
    public void initialize(Bootstrap<WazeConfig> bootstrap){}

    @Override
    public void run(WazeConfig conf,
                    Environment env) throws IOException {

        env.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        final WazeRouteService wazeRouteService = new WazeRouteService();
        final WazeNotificationService wazeNotificationService = new WazeNotificationService();
        final WazeEndPoint wazeEndPoint = new WazeEndPoint(wazeRouteService, wazeNotificationService);
        final WazeHealthCheck wazeHealthCheck = new WazeHealthCheck(wazeRouteService);

        env.healthChecks().register("waze", wazeHealthCheck);

        env.jersey().register(wazeEndPoint);
    }

}
