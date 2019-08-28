package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.model.UserInfo;
import com.apploidxxx.api.util.ErrorResponseFactory;
import com.apploidxxx.api.util.GroupChecker;
import com.apploidxxx.api.util.UserManager;
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

    @PUT
    public Response putSettings(@Valid@NotNull@QueryParam("param") String param,
                                @Valid@NotNull@QueryParam("value") String value,
                                @Valid@NotNull@QueryParam("access_token") String token){
        User user;
        try {
            user = UserManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        switch (param){
            case "group":
                value = value.toUpperCase();
                return GroupChecker.isValid(value)?setGroup(value, user): ErrorResponseFactory.getInvalidParamErrorResponse("Invalid group name");
            default:
                return ErrorResponseFactory.getInvalidParamErrorResponse("This param name not found");
        }
    }

    private Response setGroup(String group, User user){
        user.setGroupName(group);
        try {
            UserService.updateUser(user);
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

}
