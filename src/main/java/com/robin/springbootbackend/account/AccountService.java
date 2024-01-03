package com.robin.springbootbackend.account;

import com.robin.springbootbackend.auth.Credentials;
import com.robin.springbootbackend.enums.Role;
import com.robin.springbootbackend.helper.Hasher;
import com.robin.springbootbackend.helper.PasswordChecker;
import com.robin.springbootbackend.product.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final Hasher hasher;

    @Autowired
    public AccountService(AccountRepository accountRepository, Hasher hasher){
        this.accountRepository = accountRepository;
        this.hasher = hasher;
    }

    public List<Account> getAccounts(){
        List<Account> listOfAccounts = this.accountRepository.findAll();
        for(Account account : listOfAccounts){
            account.setPassword(null);
        }
        return this.accountRepository.findAll();
    }

    public Optional<Account> getAccount(UUID accountId){
        return accountRepository.findById(accountId)
                .map(receivedAccount -> {
                    receivedAccount.setPassword(null);
                    return receivedAccount;
                        }
        );
    }

    public Optional<Account> getAccountByEmail(String email){
        return accountRepository.findByEmail(email);
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
                    updatedAccount.setAddress(account.getAddress());
                    updatedAccount.setCity(account.getCity());
                    updatedAccount.setCountry(account.getCountry());
                    return accountRepository.save(updatedAccount);
                })
                .orElseThrow(() -> new IllegalStateException("account not found"));

    }

    public Account postAccount(Account account) {
        Optional<Account> accountExists = accountRepository.findByEmail(account.getUsername());
        if (accountExists.isPresent()){
            throw new IllegalStateException("Account with email: " + account.getUsername()+ " Does already exist");
        }

        PasswordChecker checker = new PasswordChecker();
        if(!checker.isValid(account.getPassword())){
            return null;
        } 
        
        String hash = hasher.hashPassword(account.getPassword());

        account.setPassword(hash);
        account.setRole(Role.USER);
        
        return accountRepository.save(account);
    }
}
