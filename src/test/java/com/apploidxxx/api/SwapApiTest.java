package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;

/**
 * @author Arthur Kupriyanov
 */
public class SwapApiTest {
    private HttpServer server;
    String user1;
    String user2;
    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        user1 = authUser("1");
        user2 = authUser("2");
    }

    @Test
    public void simple_swap(){
        // creating queue
        post(Main.BASE_URI + "api/queue?queue_name=123&fullname=123&access_token="+ user1);
        // adding participants
        put(Main.BASE_URI + "api/queue?queue_name=123&access_token=" + user1);
        put(Main.BASE_URI + "api/queue?queue_name=123&access_token=" + user2);

        // request swap from first user
        post(Main.BASE_URI + "api/swap?queue_name=123&target=2&access_token=" + user1).then().statusCode(200);
        String q1 = get(Main.BASE_URI + "api/queue?queue_name=123").then().extract().jsonPath().getString("queue_sequence");

        post(Main.BASE_URI + "api/swap?queue_name=123&target=1&access_token=" + user2).then().statusCode(200);
        String q2 = get(Main.BASE_URI + "api/queue?queue_name=123").then().extract().jsonPath().getString("queue_sequence");

        System.out.println("Before : " + q1);
        System.out.println("After: " + q2);
    }

    @After
    public void tearDown() throws Exception {
        delete(Main.BASE_URI + "api/queue?queue_name=123&access_token=" + user1);
//        deleteUser("1");
//        deleteUser("2");
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