package com.apploidxxx.api;

import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.model.Tokens;
import com.apploidxxx.api.util.Password;
import com.apploidxxx.entity.AuthorizationCode;
import com.apploidxxx.entity.Session;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.oauth.AuthorizationCodeService;
import com.apploidxxx.entity.dao.user.UserService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/")
public class AuthApi {
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response authorize(  @NotNull @QueryParam("login") String username,
                                @NotNull @QueryParam("password") String password,
                                @QueryParam("redirect_uri") String redirectUri,
                                @QueryParam("state") String state){
        User user = UserService.findByName(username);
            if (user!=null && Password.isEqual(password, user.getPassword())) {

                Session s = setUserSession(user);
                if (redirectUri == null) {
                    return Response
                            .ok(new Tokens(s.getToken(), "refresh-token", user))
                            .build();
                } else {
                    AuthorizationCode authorizationCode = new AuthorizationCode(user);
                    AuthorizationCodeService.save(authorizationCode);
                    if (state == null) state = "state";
                    URI uri = UriBuilder
                            .fromPath(redirectUri)
                            .queryParam("authorization_code", authorizationCode.getAuthCode())
                            .queryParam("state", state)
                            .build();
                    return Response.temporaryRedirect(uri).build();
                }
            } else {
                return Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorMessage("invalid_credentials", "invalid login or password"))
                        .build();
            }
    }

    @Path("/oauth")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAccessTokens(@NotNull@QueryParam("authorization_code") String authorizationCode){
        AuthorizationCode authCode = AuthorizationCodeService.findByCode(authorizationCode);
        if (authCode == null) {
            System.err.println("INVALID CODE");
            return Response.status(400).entity(new ErrorMessage("invalid_code", "Your authorization code is invalid")).build();
        }

        User user = authCode.getUser();
        AuthorizationCodeService.delete(authCode);
        Session s = setUserSession(user);
        return Response.ok().entity(new Tokens(s.getToken(), s.getRefreshToken(), user)).build();
    }

    private Session setUserSession(User user) {

        Session s;
        if (user.getSession()!=null) s = user.getSession();
        else s = new Session();

        s.generateSession(user);
        UserService.updateUser(user);
        return s;
    }
}
