package com.apploidxxx.api.oauth.google;

import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.entity.AuthorizationCode;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.oauth.AuthorizationCodeService;
import com.apploidxxx.entity.dao.user.ContactDetailsService;
import com.apploidxxx.entity.dao.user.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/google/oauth")
@Produces(MediaType.APPLICATION_JSON)
public class OAuthApi {

    @POST
    public Response handleRequest(@HeaderParam("X-Requested-With") String requestedWith,
                                  @QueryParam("code") String code) throws URISyntaxException, IOException {
        if (requestedWith == null) {
            return Response.status(400).build();
        }
        // TODO : redirect uri
        String REDIRECT_URI = "https://helios-service.herokuapp.com";

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JacksonFactory.getDefaultInstance(), new FileReader(new File(OAuthApi.class.getClassLoader().getResource("client_secret_835400034288-9ml5qeqd6ciis356v1s995345f5cbirn.apps.googleusercontent.com.json").toURI())));
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token",
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        code,
                        REDIRECT_URI)
                        .execute();

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String pictureUrl = (String) payload.get("picture");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        try {
            User user = ContactDetailsService.findByEmail(email).getUser();
            return Response.ok(createAuthorizationCodeAndGetMap(user)).build();
        } catch (UserNotFoundException e) {

            User user = new User(email, null, givenName, familyName, email);
            user.getContactDetails().setImg(pictureUrl);
            UserService.saveUser(user);

            return Response.ok(createAuthorizationCodeAndGetMap(user)).build();
        }

    }


    private Map<String, String> createAuthorizationCodeAndGetMap(User user){
        AuthorizationCode authorizationCode = new AuthorizationCode(user);
        AuthorizationCodeService.save(authorizationCode);
        Map<String, String> authCodeMap = new HashMap<>();
        authCodeMap.put("authorization_code", authorizationCode.getAuthCode());

        return authCodeMap;
    }
}
