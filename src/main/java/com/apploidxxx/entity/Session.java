package com.apploidxxx.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.Md5Crypt;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue
    long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String refreshToken;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "session")
    private User user;

    public void generateSession(User user){
        user.setSession(this);
        this.user = user;
        token = Base64.getEncoder().encodeToString(Md5Crypt.md5Crypt( (user.getUsername() + new Date().toString() + user.getFirstName()).getBytes() ).getBytes());
        refreshToken = Base64.getEncoder().encodeToString(Md5Crypt.md5Crypt( (user.getUsername() + new Date().toString() + user.getLastName()).getBytes() ).getBytes());
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
