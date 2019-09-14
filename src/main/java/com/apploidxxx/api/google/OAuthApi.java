package com.apploidxxx.api.google;

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

        String REDIRECT_URI = "http://localhost:3000";

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
        String userId = payload.getSubject();  // Use this value as a key to identify a user.
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
//        User user;
//        try {
//            user = ContactDetailsService.findByEmail(email).getUser();
//        } catch (UserNotFoundException e) {
//            Map<String, String> map = new HashMap<>();
//            map.put("email", email);
//            return Response.status(Response.Status.UNAUTHORIZED).entity(map).build();
//        }

        System.out.println(userId);
        System.out.println(email);
        System.out.println(emailVerified);
        System.out.println(name);
        System.out.println(pictureUrl);
        System.out.println(locale);
        System.out.println(familyName);
        System.out.println(givenName);

        System.out.println(
                "Your email" + email + "\n"
                + "Your username : " + email.split("@")[0] + ""
                + "Your fullname : " + name + " " + familyName + "\n"
                + "Your image : " + pictureUrl
        );

        return Response.ok().build();
    }
}
