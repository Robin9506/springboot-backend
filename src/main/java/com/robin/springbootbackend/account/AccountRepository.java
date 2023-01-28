package com.robin.springbootbackend.account;

import com.robin.springbootbackend.auth.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query("Select a FROM Account a WHERE a.username = ?1 and a.password = ?2")
    public Optional<Account> findByCredentials(String username, String password);
}