package com.robin.springbootbackend.role;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "account_role")
public class Role {
    @Id
    @Column(name = "account_id")
    private UUID AccountId;
    @Column(name = "account_role")
    private String Role;
}
