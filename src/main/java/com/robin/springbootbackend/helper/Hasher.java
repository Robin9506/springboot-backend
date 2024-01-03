package com.robin.springbootbackend.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Hasher {

    private final int SALT_LENGTH = 32;
    private final int ITERATIONS = 32;

    private Pbkdf2PasswordEncoder passwordEncoder;

    public Hasher(@Value("${password_secret}") String secret){
        this.passwordEncoder = new Pbkdf2PasswordEncoder(secret, SALT_LENGTH, ITERATIONS, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

    }

    public String hashPassword(String password){
        
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public boolean IsMatched(String password, String accountHash){
        return passwordEncoder.matches(password, accountHash);

    }


    
}
