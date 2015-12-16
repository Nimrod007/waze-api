package com.waze.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Nimrod_Lahav on 5/24/15.
 */
public class WazeException extends WebApplicationException {
    public WazeException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
