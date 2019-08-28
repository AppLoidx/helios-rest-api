package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * @author Arthur Kupriyanov
 */
public class QueueControlApiTest {

    private HttpServer server;
    private String token;
    private String anotherUserToken;
    private static String path = "api/queue/";

    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();

        token = authUser("123");
        anotherUserToken = authUser("jackson");

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE");
    }

    @Test
    public void add_admin(){

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);
        put(Main.BASE_URI + path + "testQue?action=setAdmin&admin=jackson&access_token="+token).then().statusCode(200);
        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);

    }

    @Test
    public void shuffle_queue(){
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);

        put(Main.BASE_URI + "api/queue?queue_name=testQue&access_token=" + token);
        put(Main.BASE_URI + "api/queue?queue_name=testQue&access_token=" + anotherUserToken);

        put(Main.BASE_URI + path + "testQue?action=shuffle&access_token=" + token).then().statusCode(200);
        System.out.println(get(Main.BASE_URI + "api/queue?queue_name=testQue").then().statusCode(200).extract().body().asString());

        put(Main.BASE_URI + path + "testQue?action=shuffle&access_token=" + token).then().statusCode(200);
        System.out.println(get(Main.BASE_URI + "api/queue?queue_name=testQue").then().statusCode(200).extract().body().asString());

        put(Main.BASE_URI + path + "testQue?action=shuffle&access_token=" + token).then().statusCode(200);
        System.out.println(get(Main.BASE_URI + "api/queue?queue_name=testQue").then().statusCode(200).extract().body().asString());

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void test_type_change_with_correct_type(){
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);
        put(Main.BASE_URI + path + "testQue?action=setType&type=one_week&access_token=" + token).then().statusCode(200);
        get(Main.BASE_URI + "api/queue?queue_name=testQue").then().body("generation_type", equalTo("ONE_WEEK")).and().statusCode(200);

        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @Test
    public void test_type_change_with_incorrect_type(){
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);
        put(Main.BASE_URI + path + "testQue?action=setType&type=incorrect_type&access_token=" + token)
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .and()
                .body("error", equalTo("invalid_param"));
        get(Main.BASE_URI + "api/queue?queue_name=testQue").then().body("generation_type", nullValue()).and().statusCode(200);
        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE").then().statusCode(200);
    }

    @After
    public void tearDown() throws Exception {

        deleteUser("123");
        deleteUser("jackson");

        this.server.shutdownNow();
    }

    private String authUser(String username){
        deleteUser(username);
        String username_register = Main.BASE_URI + "api/register?username=" + username +"&password=123&first_name=Arthur&last_name=Kupriyanov&email=apploidyakutsk@gmail.com";
        post(username_register).then().statusCode(200);
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