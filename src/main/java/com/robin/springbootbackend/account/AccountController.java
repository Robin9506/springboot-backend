package com.robin.springbootbackend.account;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path = "api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public Account postAccount(@RequestBody Account account) {
        return accountService.postAccount(account);
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
