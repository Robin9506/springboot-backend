package com.robin.springbootbackend.account;

import com.robin.springbootbackend.auth.Credentials;
import com.robin.springbootbackend.enums.LogType;
import com.robin.springbootbackend.enums.Repo;
import com.robin.springbootbackend.enums.Role;
import com.robin.springbootbackend.enums.RouteType;
import com.robin.springbootbackend.helper.Hasher;
import com.robin.springbootbackend.helper.Log;
import com.robin.springbootbackend.helper.LogService;
import com.robin.springbootbackend.helper.PasswordChecker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final LogService logService;
    private final Hasher hasher;

    @Autowired
    public AccountService(AccountRepository accountRepository, LogService logService, Hasher hasher){
        this.accountRepository = accountRepository;
        this.logService = logService;
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

    public void deleteAccount(UUID accountId, UUID userId, String ip) {
        boolean accountExists = accountRepository.existsById(accountId);
        if (!accountExists){
            Log log = new Log(ip, userId, LogType.DELETE, RouteType.DELETE, Repo.ACCOUNT, null, "user tried to delete account with id: " + accountId);
            this.logService.LogAction(log);
            throw new IllegalStateException("Account with ID: " + accountId + " Does not exists");
        }

        Log log = new Log(ip, userId, LogType.DELETE, RouteType.DELETE, Repo.ACCOUNT, null, "user deleted account with id: " + accountId);
        this.logService.LogAction(log);

        accountRepository.deleteById(accountId);
    }

    @Transactional
    public Account updateAccount(UUID accountId, Account account, UUID userId, String ip) {
        String hash = hasher.hashPassword(account.getPassword());
        Account currentAccount = null;

        Optional<Account> updatedAccount = accountRepository.findById(accountId);
        if(updatedAccount.isPresent()){
            currentAccount = updatedAccount.get();
            currentAccount.setUsername(account.getUsername());
            currentAccount.setPassword(hash);
            currentAccount.setAddress(account.getAddress());
            currentAccount.setCity(account.getCity());
            currentAccount.setCountry(account.getCountry());

            Log log = new Log(ip, userId, LogType.UPDATE, RouteType.PUT, Repo.ACCOUNT, null, "user " + userId +" updated account with id: " + accountId);
            this.logService.LogAction(log);

            return accountRepository.save(currentAccount);
        }
        else{
            Log log = new Log(ip, userId, LogType.UPDATE, RouteType.PUT, Repo.ACCOUNT, null, "user " + userId +" tried to update account with id: " + accountId);
            this.logService.LogAction(log);
        }

        return currentAccount;

    }

    public Account postAccount(Account account, String ip) {
        Optional<Account> accountExists = accountRepository.findByEmail(account.getUsername());
        if (accountExists.isPresent()){
            throw new IllegalStateException("Account not created");
        }

        PasswordChecker checker = new PasswordChecker();
        if(!checker.isValid(account.getPassword())){
            return null;
        } 
        
        String hash = hasher.hashPassword(account.getPassword());
        account.setPassword(hash);
        account.setRole(Role.USER);

        Log log = new Log(ip, null, LogType.CREATE, RouteType.POST, Repo.ACCOUNT, null, "user created account with email: " + account.getUsername());
        this.logService.LogAction(log);
        
        return accountRepository.save(account);
    }
}
