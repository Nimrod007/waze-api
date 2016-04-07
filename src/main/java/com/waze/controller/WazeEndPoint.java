package com.waze.controller;

import com.codahale.metrics.annotation.Timed;
import com.waze.domain.WazeRouteResponse;
import com.waze.domain.WazeRouteWithDirectionsResponse;
import com.waze.domain.WazeStreetPickerResult;
import com.waze.domain.WazeTrafficNotificationsResponse;
import com.waze.exceptions.WazeException;
import com.waze.service.WazeNotificationService;
import com.waze.service.WazeRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/waze")
@Produces(MediaType.APPLICATION_JSON)
public class WazeEndPoint {

    private final WazeRouteService wazeRouteService;
    private final WazeNotificationService wazeNotificationService;
    private final Logger log = LoggerFactory.getLogger("waze");

    public WazeEndPoint(WazeRouteService wazeRouteService, WazeNotificationService wazeNotificationService) {
        this.wazeNotificationService = wazeNotificationService;
        this.wazeRouteService = wazeRouteService;
    }

    @GET
    @Timed
    @Path("/addressList")
    public WazeStreetPickerResult getAddressList(@QueryParam("address") String address) {
        try {
            return wazeRouteService.getAddressOptionsFromFreeText(address);
        } catch (Exception ex) {
            log.error("failed to getAddressList: " + ex.getMessage());
            throw new WazeException("failed to get address list");
        }
    }

    @GET
    @Timed
    @Path("/routes")
    public WazeRouteResponse getRoutes(@QueryParam("start") String start, @QueryParam("end") String end) {
        try {
            return wazeRouteService.getRoutes(start, end);
        } catch (Exception ex) {
            log.error("failed to getRoutes: " + ex.getMessage());
            throw new WazeException("failed to get routes");
        }
    }

    @GET
    @Timed
    @Path("/routesXY")
    public WazeRouteResponse getRoutesXY(@QueryParam("startLat") String startLat,
                                         @QueryParam("startLon") String startLon,
                                         @QueryParam("endLat") String endLat,
                                         @QueryParam("endLon") String endLon) {
        try {
            return wazeRouteService.getRoutes(startLat, startLon, endLat, endLon, null, null);
        } catch (Exception ex) {
            log.error("failed to getRoutesXY: " + ex.getMessage());
        }
        throw new WazeException("failed to get address list by x,y");
    }

    @GET
    @Timed
    @Path("/routesWithDirections")
    public WazeRouteWithDirectionsResponse routesWithDirections(@QueryParam("start") String start,
                                                                @QueryParam("end") String end) {
        try {
            return wazeRouteService.getRouteWithParts(start, end);
        } catch (Exception ex) {
            log.error("failed to routesWithDirections: " + ex.getMessage());
        }
        throw new WazeException("failed to get routes with directions");
    }

    @GET
    @Timed
    @Path("/routesXYwithDirections")
    public WazeRouteWithDirectionsResponse routesXYwithDirections(
            @QueryParam("startLat") String startLat,
            @QueryParam("startLon") String startLon,
            @QueryParam("endLat") String endLat,
            @QueryParam("endLon") String endLon) {
        try {
            return wazeRouteService.getRouteWithParts(startLat, startLon, endLat, endLon, null, null);
        } catch (Exception ex) {
            log.error("failed to routesXYwithDirections: " + ex.getMessage());
        }
        throw new WazeException("failed to get routes with directions by x,y");
    }


    @GET
    @Timed
    @Path("/traffic-notifications")
    public WazeTrafficNotificationsResponse notifications(
                                                          @QueryParam("lonLeft") String lonLeft,
                                                          @QueryParam("lonRight") String lonRight,
                                                          @QueryParam("latTop") String latTop,
                                                          @QueryParam("latBottom") String latBottom) {
        try {
            return wazeNotificationService.getNotifications(lonLeft, lonRight, latTop, latBottom);
        } catch (Exception ex) {
            log.error("failed to notifications: " + ex.getMessage());
            throw new WazeException("failed to get notifications");
        }
    }
}