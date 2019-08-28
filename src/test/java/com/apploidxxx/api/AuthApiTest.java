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
public class AuthApiTest {

    private HttpServer server;

    @Before
    public void setUp() throws Exception {

        this.server = Main.startServer();
        delete(Main.BASE_URI + "api/register?username=123&password=123");
        String res = post(Main.BASE_URI + "api/register?username=123&password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com").then().extract().body().asString();
        System.out.println(res);
    }

    @Test
    public void auth_user(){
        get(Main.BASE_URI + "api/auth?login=123&password=123")
                .then()
                .statusCode(200)
                .and()
                .body("access_token", notNullValue(),
                        "refresh_token", notNullValue());
    }

    @Test
    public void invalid_auth_wrong_password(){
        get(Main.BASE_URI + "api/auth?login=123&password=333")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void invalid_auth_without_password(){
        get(Main.BASE_URI + "api/auth?login=123")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void invalid_auth_without_login(){
        get(Main.BASE_URI + "api/auth?password=123")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @After
    public void tearDown() throws Exception {
//        delete(Main.BASE_URI + "api/register?username=123&password=123");

        server.shutdownNow();
    }
}