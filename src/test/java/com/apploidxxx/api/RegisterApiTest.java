package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.post;

/**
 * @author Arthur Kupriyanov
 */
@Ignore("Неверно сделана авторизация пользователя")
public class RegisterApiTest {
    private HttpServer server;

    private String deleteUserQuery(String username){
        return String.format(Main.BASE_URI + "api/register?username=%s&password=%s",username, "123");
    }

    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        String username_123_delete = Main.BASE_URI + "api/register?username=123&password=123";
        delete(username_123_delete);
        String username_321_delete = Main.BASE_URI + "api/register?username=321&password=123";
        delete(username_321_delete);

        delete(deleteUserQuery("111"));
        delete(deleteUserQuery("222"));
        delete(deleteUserQuery("333"));
        delete(deleteUserQuery("444"));
    }

    @Test
    public void create_user_and_delete_response_successful(){
        String username_123_register = Main.BASE_URI + "api/register?username=123&password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_123_register).then().statusCode(200);
        post(username_123_register).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        String username_321_register = Main.BASE_URI + "api/register?username=321&password=123&first_name=Fedor&last_name=Bashkirov&email=testEmail@gmail.com";
        post(username_321_register).then().statusCode(200);

        String username_register_without_username = Main.BASE_URI + "api/register?password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_register_without_username).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        String username_111_register_without_email = Main.BASE_URI + "api/register?username=111&password=123&first_name=Arthur&last_name=Kupriyanov";
        post(username_111_register_without_email).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        String username_222_register_without_password = Main.BASE_URI + "api/register?username=222&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_222_register_without_password).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        String username_333_register_without_name = Main.BASE_URI + "api/register?username=333&password=123&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_333_register_without_name).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        String username_444_register_without_lastname = Main.BASE_URI + "api/register?username=444&password=123&first_name=Arthur&email=apploidyakutsk@gmail.com";
        post(username_444_register_without_lastname).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

    }

    @After
    public void tearDown() throws Exception {
        delete(deleteUserQuery("123")).then().statusCode(200);
        delete(deleteUserQuery("123")).then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());
        delete(deleteUserQuery("321")).then().statusCode(200);
        delete(deleteUserQuery("111"));
        delete(deleteUserQuery("222"));
        delete(deleteUserQuery("333"));
        delete(deleteUserQuery("444"));
        this.server.shutdownNow();
    }
}