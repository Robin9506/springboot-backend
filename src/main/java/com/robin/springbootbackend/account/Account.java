package com.robin.springbootbackend.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robin.springbootbackend.enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table
public class Account {
    @Id
    private UUID accountId;

    @Column(name = "account_username")
    private String username;

    @Transient
    @Column(name = "account_password")
    private String password;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public Account(){}
    public Account(UUID accountId, String username, String password, Role role){
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @JsonProperty(value = "account_id", index = 1)
    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty(value = "account_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
