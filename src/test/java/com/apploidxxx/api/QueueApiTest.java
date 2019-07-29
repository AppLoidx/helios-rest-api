package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Arthur Kupriyanov
 */
public class QueueApiTest {
    private HttpServer server;
    private String token;

    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();

        token = authUser("123");

    }

    @Test
    public void createQueue_token_name_fullname() {

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
        .then().statusCode(200);

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void createQueue_token_name_fullname_password() {

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue&password=123")
        .then().statusCode(200);

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void createQueue_token_name_fullname_password_generation_one_week() {

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue&password=123&generation=one_week")
        .then().statusCode(200);

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void createQueue_token_name_fullname_password_generation_two_week() {

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue&password=123&generation=two_week")
        .then().statusCode(200);

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void createQueue_without_fullname(){
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&password=123&generation=two_week")
                .then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .and()
                .body("error", equalTo("queue_not_found"),
                        "error_description", notNullValue());
    }
    @Test
    public void createQueue_without_name(){
        post(Main.BASE_URI + "api/queue?access_token="+token+"&fullname=Test%20Queue&password=123&generation=two_week")
                .then().statusCode(Response.Status.BAD_REQUEST.getStatusCode());

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .and()
                .body("error", equalTo("queue_not_found"),
                        "error_description", notNullValue());
    }

    @Test
    public void createQueue_with_unauthorized_user(){
        post(Main.BASE_URI + "api/queue?access_token=wrong-access-token&queue_name=testQue&fullname=Test%20Queue")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode())
                .and()
                .body("error", equalTo("queue_not_found"),
                        "error_description", notNullValue());
    }

    @Test
    public void createQueue_by_user_without_permissions(){
        // creating another user
        String anotherUserToken = authUser("wrong-user");

        // creating queue with main user token
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);

        // trying to delete queue without permissions
        delete(Main.BASE_URI + "api/queue?access_token="+anotherUserToken+"&queue_name=testQue&target=QUEUE").then().statusCode(Response.Status.FORBIDDEN.getStatusCode());

        // deleting queue with super user permissions (main token)
        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
        deleteUser("wrong-user");
    }

    @After
    public void tearDown() throws Exception {


        deleteUser("123");

        this.server.shutdownNow();
    }

    private String authUser(String username){
        deleteUser(username);
        String username_123_register = Main.BASE_URI + "api/register?username=" + username +"&password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_123_register).then().statusCode(200);
        return get(Main.BASE_URI + "api/auth?login=" + username +"&password=123")
                .then()
                .statusCode(200)
                .and()
                .extract().body().jsonPath().getString("access_token");

    }

    private void deleteUser(String username){
        delete(Main.BASE_URI + "api/register?username=" + username+ "&password=123");
    }
}