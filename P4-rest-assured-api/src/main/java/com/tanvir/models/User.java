package com.tanvir.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Integer id;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String avatar;
    private String name;
    private String job;

    // ── Constructors ─────────────────────────────────────────────────────────
    public User() {}

    public User(String name, String job) {
        this.name = name;
        this.job  = job;
    }

    public User(String email, String firstName, String lastName) {
        this.email     = email;
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public Integer getId()            { return id; }
    public void setId(Integer id)     { this.id = id; }

    public String getEmail()          { return email; }
    public void setEmail(String email){ this.email = email; }

    public String getFirstName()               { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName()               { return lastName; }
    public void setLastName(String lastName)  { this.lastName = lastName; }

    public String getName()           { return name; }
    public void setName(String name)  { this.name = name; }

    public String getJob()            { return job; }
    public void setJob(String job)    { this.job = job; }

    @Override
    public String toString() {
        return String.format("User{id=%d, email='%s', name='%s %s'}", id, email, firstName, lastName);
    }
}
