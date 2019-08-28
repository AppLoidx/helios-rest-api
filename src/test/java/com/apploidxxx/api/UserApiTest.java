package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Arthur Kupriyanov
 */
public class UserApiTest {

    private HttpServer server;

    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();

        delete(Main.BASE_URI + "api/register?username=123&password=123");
        String username_123_register = Main.BASE_URI + "api/register?username=123&password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_123_register).then().statusCode(200);

    }

    @Test
    public void getUserInfo_success(){
        String accessToken = get(Main.BASE_URI + "api/auth?login=123&password=123")
                .then()
                .statusCode(200)
                .and()
                .extract().body().jsonPath().getString("access_token");

        get(Main.BASE_URI + "api/user?access_token=" + accessToken)
                .then().statusCode(200)
                .and()
                .body("queues", notNullValue(),
                        "user", notNullValue());
    }

    @Test
    public void getUserInfo_failure(){
        get(Main.BASE_URI + "api/user?username=123&password=wrong-password")
                .then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void setGroupAfterRegistrationWithoutGroup(){
        String accessToken = get(Main.BASE_URI + "api/auth?login=123&password=123")
                .then()
                .statusCode(200)
                .and()
                .extract().body().jsonPath().getString("access_token");

        put(Main.BASE_URI + "api/user?access_token=" + accessToken
        + "&param=group&value=123").then().statusCode(200);
    }

    @Test
    public void setGroupAfterRegistrationWithGroup(){
        String username_with_group_register = Main.BASE_URI + "api/register?username=with&password=group&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com&group=144";
        post(username_with_group_register).then().statusCode(200);
        String accessToken = get(Main.BASE_URI + "api/auth?login=with&password=group")
                .then()
                .statusCode(200)
                .and()
                .extract().body().jsonPath().getString("access_token");

        put(Main.BASE_URI + "api/user?access_token=" + accessToken
                + "&param=group&value=123").then().statusCode(200);
        String res = get(Main.BASE_URI + "api/user?access_token=" + accessToken).then().statusCode(200).and().extract().body().asString();
        System.out.println(res);
        delete(Main.BASE_URI + "api/register?username=with&password=group");

    }

    @After
    public void tearDown() throws Exception {

        delete(Main.BASE_URI + "api/register?username=123&password=123");

        this.server.shutdownNow();
    }
}