package com.robin.springbootbackend.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FileHelper {

    private final String PNG_HEX = "89 50 4E 47 0D 0A 1A 0A";
    private final String JPEG_HEX = "FF D8 FF E0 00 10 4A 46 49 46 00 01";    

    private String storePath;

    public FileHelper(@Value("${image_store_path}") String storePath){
        this.storePath = storePath;

    }

    public String encodeFile(String filePath){
        File file = new File(storePath + "/" + filePath);

        byte[] imageBytes = null;
        try {
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return "";
        }
        String base64String = Base64.getEncoder().encodeToString(imageBytes);

        return base64String;
    }

    public byte[] decodeFile(String base64String){
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        return decodedBytes;
    }

    public boolean checkFileHex(byte[] fileBytes){
        boolean isPng = false;
        boolean isJpeg = false;

        StringBuilder hexString = new StringBuilder();

        for(byte b : fileBytes) {
            hexString.append(String.format("%02X ", b));
        }

        String pngHex = hexString.substring(0, 23);        
        String jpegHex = hexString.substring(0, 35);

        isPng = isValidExtensionFile(PNG_HEX, pngHex);
        isJpeg = isValidExtensionFile(JPEG_HEX, jpegHex);

        return isPng || isJpeg;
    }

    public boolean isValidExtensionFile(String extensionHex, String hex){
        if (extensionHex.equals(hex) ) {
            return true;
        }

        return false;
    }

    public String convertBase64ToFile(byte[] file){
         SecureRandom random = new SecureRandom();
         String fileName = new BigInteger(130, random).toString(32);

        try (OutputStream stream = new FileOutputStream(storePath + "/" + fileName +".png")) {
            stream.write(file);
        }
        catch (Exception e) {
            return "";
        }

        return fileName +".png";
    }
}
