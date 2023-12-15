package com.robin.springbootbackend.auth;

import com.nimbusds.jose.shaded.gson.Gson;
import com.robin.springbootbackend.account.Account;
import com.robin.springbootbackend.account.AccountService;
import com.robin.springbootbackend.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthService {

    private final JwtService jwtService;

    private final AccountService accountService;

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService){

        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    public Token createToken(Account account){
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Triple R Games")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(account.getAccountId().toString())
                .claim("role", account.getRole())
                .build();

        String token = this.jwtService.encodeJWT(claims);

        return new Token(token);

    }

    public Token loginAccount(Credentials credentials){
        Optional<Account> accountOptional = this.accountService.getAccountByCredentials(credentials);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            return createToken(account);
        }
        return null;
    }
}
