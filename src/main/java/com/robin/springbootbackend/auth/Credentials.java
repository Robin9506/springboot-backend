package com.robin.springbootbackend.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    @JsonProperty("_username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("_password")
    public void setPassword(String password) {
        this.password = password;
    }
}
