package com.apploidxxx.entity;

import com.apploidxxx.entity.queue.Queue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Arthur Kupriyanov
 */
@Table(name="users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User(String username, String password, String firstName, String lastName, String email){
        this(username, password, firstName, lastName);
        this.contactDetails = new ContactDetails(this, email);
    }
    public User(String username, String password, String firstName, String lastName, String email, String group){
        this(username, password, firstName, lastName, email);
        this.groupName = group;
    }

    @Id
    @GeneratedValue
    Long id;


    @ManyToMany(mappedBy = "superUsers", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonbTransient
    private Set<Queue> queueSuper;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonbTransient
    private UserData userdata = new UserData(this);

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonbTransient
    private Set<Queue> queueMember;

    @JsonProperty("user_type")
    @JsonbProperty("user_type")
    private UserType userType = UserType.STUDENT;

    @JoinColumn(name="session")
    @JsonIgnore
    @JsonbTransient
    @OneToOne(cascade = CascadeType.ALL)
    private Session session;

    @JoinColumn(name = "contactDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("contact_details")
    @JsonbProperty("contact_details")
    private ContactDetails contactDetails;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    private String groupName;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    @JsonbTransient
    private String password;

    @Column(name = "firstName", nullable = false)
    @JsonProperty("first_name")
    @JsonbProperty("first_name")
    private String firstName;

    @Column(name = "lastName")
    @JsonProperty("last_name")
    @JsonbProperty("last_name")
    private String lastName;

    public Set<Queue> getQueueSuper() {
        if (queueSuper == null){
            queueSuper = new HashSet<>();
        }
        return queueSuper;
    }

    public void setQueueSuper(Set<Queue> queueSuper) {
        this.queueSuper = queueSuper;
    }

    public Set<Queue> getQueueMember() {
        if (queueMember ==null){
            queueMember = new HashSet<>();
        }
        return queueMember;
    }

    public void addVkId(long vkid){
        this.contactDetails = new ContactDetails(this, this.contactDetails.getEmail(), vkid);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            return ((User) obj).getUsername().equals(this.username);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
