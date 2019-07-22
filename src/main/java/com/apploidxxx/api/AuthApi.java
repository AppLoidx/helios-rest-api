package com.apploidxxx.api;

import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.entity.Session;
import com.apploidxxx.entity.Tokens;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.SessionService;
import com.apploidxxx.entity.dao.user.UserService;

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
@Path("/api/auth")
public class AuthApi {
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response authorize(  @NotNull @QueryParam("username") String username,
                                @NotNull @QueryParam("password") String password){
            UserService service = new UserService();
            User user = service.findByName(username);
            if (user!=null && password.equals(user.getPassword())) {
                SessionService ss = new SessionService();

                Session s;
                if (user.getSession()!=null){
                    s = user.getSession();
                    s.generateSession(user);
                    ss.updateSession(s);
                } else {
                    s = new Session();
                    s.generateSession(user);
                    new UserService().updateUser(user);
                    ss.saveSession(s);
                }
                return Response
                        .ok(new Tokens(s.getToken(), "refresh-token", user))
                        .build();
            } else {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorMessage("invalid_credentials", "invalid username or password"))
                        .build();
            }
    }
}
