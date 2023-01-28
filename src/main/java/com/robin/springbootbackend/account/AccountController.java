package com.robin.springbootbackend.account;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping(path = "{accountId}")
    public Optional<Account> getAccount(@PathVariable("accountId") UUID accountId){
        return accountService.getAccount(accountId);
    }

    @DeleteMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteAccount(@PathVariable("accountId") UUID accountId){
        accountService.deleteAccount(accountId);
    }

    @PutMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateAccount(@PathVariable("accountId") UUID accountId,
                              @RequestBody Account account){
        accountService.updateAccount(accountId, account);
    }
}