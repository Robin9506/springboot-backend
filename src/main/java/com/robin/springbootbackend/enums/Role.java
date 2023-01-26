package com.robin.springbootbackend.enums;

import java.util.Objects;

public enum Role {
    USER,
    DEVELOPER,
    ADMIN;

    public Role castStringToRole(String role){
        if (Objects.equals(role, "USER")){
            return USER;
        }
        if (Objects.equals(role, "DEVELOPER")){
            return DEVELOPER;
        }
        if (Objects.equals(role, "ADMIN")){
            return ADMIN;
        }

        return USER;
    }
}
