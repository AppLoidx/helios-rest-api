package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.util.UserInfo;
import com.apploidxxx.api.util.UserSessionManager;
import com.apploidxxx.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserApi {
    @GET
    public Response getInfo(@QueryParam("access_token") String token,
                            @HeaderParam("Authorization") String authorization){

        User user ;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        return Response.ok(new UserInfo( user)).build();

    }
}
