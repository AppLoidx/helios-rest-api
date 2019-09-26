package com.apploidxxx.entity.dao.user;

import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.entity.ContactDetails;
import com.apploidxxx.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Arthur Kupriyanov
 */
public class ContactDetailsServiceTest {
    private final User user = new User("123", "123", "123", "123", "");
    @Before
    public void setUp() throws Exception {

        ContactDetails c = new ContactDetails();
        c.setEmail("app@mail.ru");
        user.setContactDetails(c);
        UserService.saveUser(user);

    }

    @Test
    public void findByEmail() throws UserNotFoundException {

            assertNotNull(ContactDetailsService.findByEmail("app@mail.ru"));

    }

    @After
    public void tearDown() throws Exception {
        UserService.deleteUser(user);
    }
}