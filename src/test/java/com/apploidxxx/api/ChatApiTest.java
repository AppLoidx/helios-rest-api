package com.apploidxxx.api;

import com.apploidxxx.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;

/**
 * @author Arthur Kupriyanov
 */
@Ignore("Неверно сделана авторизация пользователя")
public class ChatApiTest {
    private HttpServer server;
    private String path = "api/chat/testQue";
    private String token;
    private String anotherUserToken;
    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        token = authUser("123");
        anotherUserToken = authUser("111");
        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE");

        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue")
                .then().statusCode(200);

    }

    @Test
    public void add_message_with_permissions_and_not(){
        put(Main.BASE_URI + path + "?message=Hi&access_token=" + token).then().statusCode(200);
        put(Main.BASE_URI + path + "?message=It's me&access_token=" + token).then().statusCode(200);
        put(Main.BASE_URI + path + "?message=Hello&access_token=" + anotherUserToken).then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
        get(Main.BASE_URI + path + "?lastMsgId=-1").then().statusCode(200);

    }

    @Test
    public void add_message_as_member_and_not(){
        put(Main.BASE_URI + path + "?message=Hi&access_token=" + token).then().statusCode(200);
        put(Main.BASE_URI + path + "?message=It's me&access_token=" + token).then().statusCode(200);

        // trying add message via not a member user
        put(Main.BASE_URI + path + "?message=Hello&access_token=" + anotherUserToken).then().statusCode(Response.Status.FORBIDDEN.getStatusCode());

        // put new member
        put(Main.BASE_URI + "api/queue?queue_name=testQue&access_token=" + anotherUserToken).then().statusCode(200);
        // sending message via member
        put(Main.BASE_URI + path + "?message=Yep! I joined to queue&access_token=" + token).then().statusCode(200);
        // delete user from members
        delete(Main.BASE_URI + "api/queue?queue_name=testQue&target=USER&access_token=" + anotherUserToken).then().statusCode(200);

        get(Main.BASE_URI + "api/queue?queue_name=testQue").then().statusCode(200);

        // trying send message
        put(Main.BASE_URI + path + "?message=I'm trying send message! False at all&access_token=" + anotherUserToken).then().statusCode(Response.Status.FORBIDDEN.getStatusCode());
        // register user as admin
        put(Main.BASE_URI + "api/queue/testQue?action=setAdmin&admin=111&access_token=" + token).then().statusCode(200);
        // sending message as admin
        put(Main.BASE_URI + path + "?message=I'm fine now&access_token=" + token).then().statusCode(200);

        String res = get(Main.BASE_URI + path + "?lastMsgId=-1").then().statusCode(200).extract().body().asString();
//        System.out.println(res);
    }

    @After
    public void tearDown() throws Exception {

        System.out.println(delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE")
                .then().extract().body().asString());
        deleteUser("123");
        deleteUser("111");
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