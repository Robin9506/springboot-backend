package com.robin.springbootbackend.auth;

import com.nimbusds.jwt.JWT;
import com.robin.springbootbackend.account.Account;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping
    public Token loginAccount(@RequestBody Credentials credentials, HttpServletRequest request) {
        return authService.loginAccount(credentials, request.getRemoteAddr());
    }
}
