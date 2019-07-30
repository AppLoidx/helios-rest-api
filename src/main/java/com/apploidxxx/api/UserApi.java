package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.util.UserInfo;
import com.apploidxxx.api.util.UserManager;
import com.apploidxxx.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserApi {
    @GET
    public Response getInfo(@Valid @NotNull @QueryParam("access_token") String token) {

        User user;
        try {
            user = UserManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok(new UserInfo(user)).build();

    }
}
