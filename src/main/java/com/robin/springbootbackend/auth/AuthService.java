package com.robin.springbootbackend.auth;

import com.robin.springbootbackend.account.Account;
import com.robin.springbootbackend.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AccountService accountService;

    @Autowired
    public AuthService(AccountService accountService){
        this.accountService = accountService;
    }

    public Optional<Account> loginAccount(Credentials credentials){
        return this.accountService.getAccountByCredentials(credentials);
    }

}
