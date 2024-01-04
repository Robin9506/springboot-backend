package com.robin.springbootbackend.auth;

import com.robin.springbootbackend.account.Account;
import com.robin.springbootbackend.account.AccountService;
import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.Hasher;
import com.robin.springbootbackend.helper.Log;
import com.robin.springbootbackend.helper.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthService {

    private final JwtService jwtService;

    private final AccountService accountService;

    private final LogService logService;

    private final Hasher hasher;

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService, LogService logService, Hasher hasher){

        this.accountService = accountService;
        this.jwtService = jwtService;
        this.logService = logService;
        this.hasher = hasher;
    }

    public Token createToken(Account account){
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Triple R Games")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(account.getAccountId().toString())
                .build();

        String token = this.jwtService.encodeJWT(claims);

        return new Token(token);

    }

    public Token loginAccount(Credentials credentials, String ip){
        Optional<Account> accountOptional = this.accountService.getAccountByEmail(credentials.getUsername());
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();

            boolean passwordMatched = hasher.IsMatched(credentials.getPassword(), account.getPassword());
            
            if (passwordMatched) {
                return createToken(account);
                
            }
            else{
                Log log = new Log(ip, null, LogType.AUTH, RouteType.POST, Repo.AUTH, null, "user tried to login with username: " + credentials.getUsername());
                this.logService.LogAction(log);
                return null; 
            }  
        }
        return null;
    }
}
