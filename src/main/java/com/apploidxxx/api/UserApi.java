package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.util.UserInfo;
import com.apploidxxx.api.util.UserSessionManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public Response getInfo(@Valid@NotNull@QueryParam("access_token") String token){

        User user ;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        return Response.ok(new UserInfo( user)).build();

    }
    // TODO : delete user

    @DELETE
    public Response deleteUser(@Valid@NotNull@QueryParam("access_token") String token){
        User user ;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }

        UserService us = new UserService();
        us.deleteUser(user);
        return Response.ok().build();
    }
}
