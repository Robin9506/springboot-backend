package com.robin.springbootbackend.account;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping(path = "{accountId}")
    public Optional<Account> getAccount(@PathVariable("accountId") UUID accountId){
        return accountService.getAccount(accountId);
    }

    @DeleteMapping(path = "{accountId}")
    public void deleteAccount(@PathVariable("accountId") UUID accountId){
        accountService.deleteAccount(accountId);
    }

}
