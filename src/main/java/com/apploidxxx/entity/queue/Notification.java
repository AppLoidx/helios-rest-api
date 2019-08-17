package com.apploidxxx.entity.queue;

import com.apploidxxx.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    public Notification(User author, String message){
        this.author = author;
        this.userId = author==null?"null":author.getUsername();
        this.message = message;
    }

    @JsonIgnore
    @JsonbTransient
    @ManyToOne
    private Queue queue;

    @JsonIgnore
    @JsonbTransient
    @ManyToOne
    private User author;

    @JsonProperty("user")
    @JsonbProperty("user")
    private String userId;

    @Column(nullable = false)
    private String message;
}
