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

        System.out.println(get(Main.BASE_URI + "api/queue?queue_name=testQue").then().statusCode(200).extract().body().asString());

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