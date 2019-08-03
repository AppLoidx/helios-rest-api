package com.apploidxxx.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/user/data")
@Produces(MediaType.APPLICATION_JSON)
public class UserDataAPI {

    @GET
    public Response get(){
        return null;
    }
}
