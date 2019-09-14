package com.apploidxxx.entity;

import lombok.Data;
import org.apache.commons.codec.digest.Md5Crypt;
import org.glassfish.jersey.internal.util.Base64;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Data
public class AuthorizationCode {
    @Id
    @GeneratedValue
    private Long id;
    public AuthorizationCode(){}
    public AuthorizationCode(User user){
        this.user = user;
        authCode = new String(Base64.encode(Md5Crypt.md5Crypt((user.getUsername() + user.getFirstName() + new Date()).getBytes()).getBytes()));
    }

    @OneToOne
    private User user;

    @Column
    private String authCode;


}
