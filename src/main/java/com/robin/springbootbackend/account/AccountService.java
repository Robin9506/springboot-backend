package com.robin.springbootbackend.account;

import com.robin.springbootbackend.auth.Credentials;
import com.robin.springbootbackend.product.Product;
import jakarta.transaction.Transactional;
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
        List<Account> listOfAccounts = this.accountRepository.findAll();
        for(Account account : listOfAccounts){
            account.setPassword("*".repeat(account.getPassword().length()));
        }
        return this.accountRepository.findAll();
    }

    public Optional<Account> getAccount(UUID accountId){
        return accountRepository.findById(accountId)
                .map(receivedAccount -> {
                    receivedAccount.setPassword("*".repeat(receivedAccount.getPassword().length()));
                    return receivedAccount;
                        }

        );
    }

    public Optional<Account> getAccountByCredentials(Credentials credentials){
        return accountRepository.findByCredentials(credentials.getUsername(), credentials.getPassword());
    }

    public void deleteAccount(UUID accountId) {
        boolean accountExists = accountRepository.existsById(accountId);
        if (!accountExists){
            throw new IllegalStateException("Account with ID: " + accountId + " Does not exists");
        }
        accountRepository.deleteById(accountId);
    }

    @Transactional
    public Account updateAccount(UUID accountId, Account account) {
        return accountRepository.findById(accountId)
                .map(updatedAccount -> {
                    updatedAccount.setUsername(account.getUsername());
                    updatedAccount.setPassword(account.getPassword());
                    updatedAccount.setRole(account.getRole());
                    updatedAccount.setAddress(account.getAddress());
                    updatedAccount.setCity(account.getCity());
                    updatedAccount.setCountry(account.getCountry());
                    return accountRepository.save(updatedAccount);
                })
                .orElseThrow(() -> new IllegalStateException("account not found"));

    }

    public Account postAccount(Account account) {
//        boolean accountExists = accountRepository.existsById(accountId);
//        if (!accountExists){
//            throw new IllegalStateException("Account with ID: " + accountId + " Does not exists");
//        }
        return accountRepository.save(account);
    }
}
