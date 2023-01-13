package com.robin.springbootbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class JwtDecodeHelper {

    private JwtDecoder jwtDecoder;

    @Autowired
    public JwtDecodeHelper(JwtDecoder jwtDecoder){
        this.jwtDecoder = jwtDecoder;
    }

    public void decodeJWT(String token){
        this.jwtDecoder.decode(token);
    }
}
