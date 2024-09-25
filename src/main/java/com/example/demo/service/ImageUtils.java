package com.example.demo.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class ImageUtils {
    public static void decodeBase64AndSave(String base64Data, String filePath) {
        try {
            // Remove the prefix "data:image/png;base64," from the base64 string
            String base64Image = base64Data.split(",")[1];

            // Decode the base64 string to bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Save the bytes to a file
            Files.write(Path.of(filePath), imageBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
