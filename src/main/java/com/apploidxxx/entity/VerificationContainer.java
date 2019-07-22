package com.apploidxxx.entity;

import javax.persistence.*;

/**
 * @author Arthur Kupriyanov
 */
@Table
@Entity
public class VerificationContainer {
    public VerificationContainer(){}
    public VerificationContainer(String verificationCode, User user){
        this.verificationCode = verificationCode;
        this.user = user;
    }

    @Id
    @OneToMany
    @Column(unique = true)
    private User user;

    @Column
    private String verificationCode;

    public boolean verificate(String verificationCode){
        return this.verificationCode.equals(verificationCode);
    }

    public User getUser(){
        return user;
    }


}
