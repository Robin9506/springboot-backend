package com.robin.springbootbackend.account;

import com.robin.springbootbackend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts(){
        return this.accountRepository.findAll();
    }

    public Optional<Account> getAccount(UUID accountId){
        return accountRepository.findById(accountId);
    }

    public void deleteAccount(UUID accountId) {
        boolean accountExists = accountRepository.existsById(accountId);
        if (!accountExists){
            throw new IllegalStateException("Account with ID: " + accountId + " Does not exists");
        }
        accountRepository.deleteById(accountId);
    }
}
