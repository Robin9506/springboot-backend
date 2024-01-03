package com.robin.springbootbackend.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileHelper {

    private final String PNG_HEX = "89 50 4E 47 0D 0A 1A 0A";
    private final String JPEG_HEX = "FF D8 FF E0 00 10 4A 46 49 46 00 01";    

    public String encodeFile(String filePath) throws IOException{
        File file = new File(filePath);

        byte[] imageBytes = Files.readAllBytes(file.toPath());
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

        if (PNG_HEX.equals(pngHex) ) {
            isPng = true;
        }
        
        if (JPEG_HEX.equals(jpegHex)) {
            isJpeg = true;
        }

        return isPng || isJpeg;
    }
    
}
