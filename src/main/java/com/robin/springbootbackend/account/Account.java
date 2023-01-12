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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accountId;

    @Column(name = "account_username")
    private String username;

    @Column(name = "account_password")
    private String password;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "account_address")
    private String address;

    @Column(name = "account_city")
    private String city;

    @Column(name = "account_country")
    private String country;

    public Account(){}

    public Account(String username, String password, Role role, String address, String city, String country){
        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
        this.city = city;
        this.country = country;
    }
    public Account(UUID accountId, String username, String password, Role role, String address, String city, String country){
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    @JsonProperty(value = "account_id", index = 1)
    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("_username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("_password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("role")
    public Role getRole() {
        return role;
    }

    @JsonProperty("_role")
    public void setRole(Role role) {
        this.role = role;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("_address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("_city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("_country")
    public void setCountry(String country) {
        this.country = country;
    }
}
