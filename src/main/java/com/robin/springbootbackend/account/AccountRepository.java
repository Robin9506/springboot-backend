package com.robin.springbootbackend.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.robin.springbootbackend.enums.Role;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query("Select a FROM Account a WHERE a.username = ?1 and a.password = ?2")
    public Optional<Account> findByCredentials(String username, String password);

    @Query("Select a FROM Account a WHERE a.username = ?1")
    public Optional<Account> findByEmail(String email);

    @Query("Select a FROM Account a WHERE a.accountId = ?1")
    public Optional<Account> findAccountById(UUID accountId);

    @Query("Select a FROM Account a")
    public List<Optional<Account>> findAllAccounts();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Account WHERE accountId = ?1")
    public void deleteAccountById(UUID accountID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Account SET username = ?1, password = ?2, address = ?3, city = ?4, country = ?5 WHERE accountId = ?6")
    public void updateAccount(String username, String password, String address, String city, String country, UUID accountID);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Account (username, password, role, address, city, country) VALUES (?1, ?2, ?3, ?4, ?5, ?6)")
    public void insertAccount(String username, String password, String role, String address, String city, String country);
}
