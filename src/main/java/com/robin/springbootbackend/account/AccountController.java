package com.robin.springbootbackend.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://triplergames.com"}, maxAge = 3600)
@RequestMapping(path = "api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public Account postAccount(@RequestBody Account account, HttpServletRequest request) {
        return accountService.postAccount(account, request.getRemoteAddr());
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Optional<Account>> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Account> getAccount(@PathVariable("accountId") UUID accountId){
        return accountService.getAccount(accountId);
    }

    @GetMapping(path = "/own")
    public Optional<Account> getOwnAccount(Authentication authentication){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        return accountService.getAccount(accountId);
    }

    @DeleteMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAccount(@PathVariable("accountId") UUID accountId, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(token.getSubject());
        
        accountService.deleteAccount(accountId, userId, request.getRemoteAddr());
    }

    @DeleteMapping(path = "/own")
    public void deleteOwnAccount(Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(token.getSubject());

        accountService.deleteAccount(userId, userId, request.getRemoteAddr());
    }

    @PutMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateAccount(@PathVariable("accountId") UUID accountId,
                              @RequestBody Account account, Authentication authentication, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(token.getSubject());
        
        accountService.updateAccount(accountId, account, userId, request.getRemoteAddr());
    }

    @PutMapping(path = "/own")
    public void updateOwnAccount(Authentication authentication, 
                                @RequestBody Account account, HttpServletRequest request){
        Jwt token = (Jwt) authentication.getPrincipal();
        UUID accountId = UUID.fromString(token.getSubject());

        accountService.updateAccount(accountId, account, accountId, request.getRemoteAddr());
    }
}
