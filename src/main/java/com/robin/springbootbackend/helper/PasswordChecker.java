package com.robin.springbootbackend.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordChecker {
    private final int MIN_LENGTH = 8;

    public PasswordChecker(){}

    public boolean isValid(String password){
        if (password.length() < MIN_LENGTH) {
            return false;
        }
        
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher digitMatcher = digit.matcher(password);
        Matcher specialMatcher = special.matcher(password);

        hasNumber = digitMatcher.find();
        hasSpecialChar = specialMatcher.find();

        for (int character = 0; character < password.toCharArray().length; character++) {
            char letter = password.toCharArray()[character];
            if(Character.isUpperCase(letter)) hasUppercase = true;
            if(Character.isLowerCase(letter)) hasLowercase = true;
        }

        return (
            hasUppercase && 
            hasLowercase &&
            hasNumber && 
            hasSpecialChar);

    }
    
}
