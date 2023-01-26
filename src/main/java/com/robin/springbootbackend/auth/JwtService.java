package com.robin.springbootbackend.auth;

import com.robin.springbootbackend.account.AccountService;
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
        return this.jwtDecoder.decode(token);
    }
}
