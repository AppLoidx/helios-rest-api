package com.apploidxxx.api;

import com.apploidxxx.Main;
import com.apploidxxx.api.model.UserNotes;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

// TODO: Add teacher user type test with private notes

/**
 * @author Arthur Kupriyanov
 */
public class UserNoteApiTest {
    private HttpServer server;
    private String token ;
    private String anotherToken;
    private String path = "api/user/note";
    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        token = authUser("123");
        anotherToken = authUser("111");
        delete(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&target=QUEUE");
        post(Main.BASE_URI + "api/queue?access_token="+token+"&queue_name=testQue&fullname=Test%20Queue&password=123&generation=one_week")
                .then().statusCode(200);

    }

    @Test
    public void add_note(){
        String noteId = createNote(token);
        delete(Main.BASE_URI + path + "?note_id=" + noteId + "&access_token=" + token).then().statusCode(200);
    }

    @Test
    public void get_note(){
        createNote(token);
        UserNotes notes = get(Main.BASE_URI + path + "?type=PUBLIC&username=111&access_token=" + anotherToken).then().statusCode(200).and().extract().body().as(UserNotes.class);
        assertEquals(1, notes.getNotes().size());

        createNote(token);
        notes = get(Main.BASE_URI + path + "?type=PUBLIC&username=111&access_token=" + anotherToken).then().statusCode(200).and().extract().body().as(UserNotes.class);
        assertEquals(2, notes.getNotes().size());
    }

    @Test
    public void private_notes(){
        createNote(token);
        get(Main.BASE_URI + path + "?type=PRIVATE&username=111&access_token=" + anotherToken).then().statusCode(Response.Status.FORBIDDEN.getStatusCode());

    }
    @After
    public void tearDown() throws Exception {
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

    private String createNote(String token){
        return  with().body("This is my note").request("POST", Main.BASE_URI + path + "?note_type=PUBLIC&target=111&access_token=" + token).then().statusCode(200).extract().jsonPath().getString("note_id");
    }
    private String createPrivateNote(String token){
        return  with().body("This is my note").request("POST", Main.BASE_URI + path + "?note_type=PRIVATE&target=111&access_token=" + token).then().statusCode(200).extract().jsonPath().getString("note_id");
    }

}