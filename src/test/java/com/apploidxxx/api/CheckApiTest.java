package com.apploidxxx.api;

import com.apploidxxx.Main;
import com.apploidxxx.entity.ContactDetails;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.UserService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

/**
 * @author Arthur Kupriyanov
 */
public class CheckApiTest {
    private HttpServer server;
    private final User user = new User("123", "123", "123", "123");
    @Before
    public void setUp() throws Exception {
        this.server = Main.startServer();
        ContactDetails c = new ContactDetails();
        c.setEmail("app@mail.ru");
        user.setContactDetails(c);
        UserService.saveUser(user);
    }

    @Test
    public void email_exist(){
        String res1 = get(Main.BASE_URI + "api/check?check=email_exist&email=app@mail.ru").then().extract().jsonPath().getString("exist");
        assertEquals(res1, "true");
        String res2 = get(Main.BASE_URI + "api/check?check=email_exist&email=invalid-mail").then().extract().jsonPath().getString("exist");
        assertEquals(res2, "false");
    }

    @After
    public void tearDown() throws Exception {
        UserService.deleteUser(user);

        this.server.shutdownNow();
    }
}