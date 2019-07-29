package com.apploidxxx.entity;


import com.apploidxxx.api.util.PasswordChecker;

import javax.persistence.*;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Arthur Kupriyanov
 */
@Entity
public class Session {
    @Id
    @GeneratedValue
    long id;

    @Column
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void generateSession(User user){
        user.setSession(this);
        this.user = user;
        int randomNumber = (int) (Math.random() * 1520);
        token = Base64.getEncoder().encodeToString(
                (user.getUsername()
                        + user.getLastName() +
                        PasswordChecker.hashPassword(user.getPassword()) + "salt" + randomNumber).getBytes());


    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(user, session.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
