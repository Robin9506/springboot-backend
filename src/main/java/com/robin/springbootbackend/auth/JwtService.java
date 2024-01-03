package com.robin.springbootbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;

    }

    public String encodeJWT(JwtClaimsSet claims){
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decodeJWT(String token){
        try{
            return this.jwtDecoder.decode(token);
        } catch (JwtException e) {
            System.out.println("jwt is faulty");
        }
        return null;
    }
}
