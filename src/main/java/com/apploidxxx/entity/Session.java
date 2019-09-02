package com.apploidxxx.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.Md5Crypt;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;

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

}
