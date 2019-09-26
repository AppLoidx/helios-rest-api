package com.apploidxxx.api.oauth.vk;

import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.api.oauth.vk.model.AccessToken;
import com.apploidxxx.api.oauth.vk.model.UserInfo;
import com.apploidxxx.api.oauth.vk.model.VkUser;
import com.apploidxxx.entity.AuthorizationCode;
import com.apploidxxx.entity.ContactDetails;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.oauth.AuthorizationCodeService;
import com.apploidxxx.entity.dao.user.ContactDetailsService;
import com.apploidxxx.entity.dao.user.UserService;
import io.restassured.RestAssured;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author Arthur Kupriyanov
 */
@Path("api/vk/oauth")
public class OAuthApi {

    @Path("/redirect")
    @GET
    public Response getRedirectUri(@NotNull@QueryParam("redirect_uri") String redirectUri) throws URISyntaxException {

        URI uri = new URI(VkUriBuilder.getCodeTokenPath(redirectUri));
        return Response.temporaryRedirect(uri).build();
    }

    @GET
    public Response redirectService(@QueryParam("code") String accessCode,
                                    @QueryParam("state") String stateRedirectUrl,
                                    @QueryParam("error") String error) throws URISyntaxException {
        if (accessCode == null){
            if ("access_denied".equals(error)){
                return Response.temporaryRedirect(new URI("/html/external/login.html")).build();
            }
            return Response.serverError().build();
        }


        AccessToken token = RestAssured.get(VkUriBuilder.getAccessTokenPath(accessCode)).then()
                .extract().body().as(AccessToken.class);

        if (token.getAccessToken() == null){
            return Response.serverError().build();
        }

        try {
            ContactDetails contactDetails = ContactDetailsService.findByVkId(token.getUserId());
            User heliosUser = contactDetails.getUser();
            return redirectWithAuthCode(heliosUser, stateRedirectUrl);
        } catch (UserNotFoundException e) {
            // user not set up the vkid
        }

        if (token.getEmail() != null){
            try {
                ContactDetails contactDetails = ContactDetailsService.findByEmail(token.getEmail());
                User heliosUser = contactDetails.getUser();
                heliosUser.getContactDetails().setVkontakteId(Long.parseLong(token.getUserId()));
                UserService.updateUser(heliosUser);
                return redirectWithAuthCode(heliosUser, stateRedirectUrl);
            } catch (UserNotFoundException e) {
                // user not set up email or unregistered
            }
        }

        UserInfo user = RestAssured.get(VkUriBuilder.getUserInfoPath(token.getAccessToken(), token.getUserId()))
                .then().extract().body().as(UserInfo.class);

        VkUser vkUser = user.getResponse().get(0);

        if (vkUser.getScreenName() == null) {
            vkUser.setScreenName("vk" + vkUser.getId());
        }
        User heliosUser = new User(vkUser.getScreenName(), null, vkUser.getFirstName(), vkUser.getLastName());
        if (token.getEmail() != null) heliosUser.getContactDetails().setEmail(token.getEmail());
        heliosUser.getContactDetails().setImg(vkUser.getPhoto100Url());
        UserService.saveUser(heliosUser);
        return redirectWithAuthCode(heliosUser, stateRedirectUrl);
    }

    private Response redirectWithAuthCode(User user, String redirectUrl) throws URISyntaxException {
        AuthorizationCode authorizationCode = new AuthorizationCode(user);
        AuthorizationCodeService.save(authorizationCode);
        return Response.temporaryRedirect(new URI(redirectUrl + "?authorization_code=" + authorizationCode.getAuthCode())).build();
    }

}
