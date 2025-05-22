package com.github.manimovassagh.immo_finder.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class PhotoFileTest {

    @Test
    void testPhotoFilesExist() {
        // Try different paths to find the test images
        String[] possiblePaths = {
            "src/main/resources/test-images/photo1.jpg",
            "rent-service/src/main/resources/test-images/photo1.jpg",
            "../src/main/resources/test-images/photo1.jpg",
            "../../src/main/resources/test-images/photo1.jpg"
        };
        
        boolean found = false;
        String workingPath = null;
        
        for (String path : possiblePaths) {
            File file = new File(path);
            System.out.println("[DEBUG_LOG] Trying path: " + file.getAbsolutePath());
            System.out.println("[DEBUG_LOG] File exists: " + file.exists());
            
            if (file.exists()) {
                found = true;
                workingPath = path;
                break;
            }
        }
        
        assertTrue(found, "Could not find photo1.jpg in any of the tried paths");
        System.out.println("[DEBUG_LOG] Working path: " + workingPath);
        
        // Now check photo2.jpg using the working path
        String photo2Path = workingPath.replace("photo1.jpg", "photo2.jpg");
        File photo2 = new File(photo2Path);
        System.out.println("[DEBUG_LOG] Photo2 path: " + photo2.getAbsolutePath());
        System.out.println("[DEBUG_LOG] Photo2 exists: " + photo2.exists());
        
        assertTrue(photo2.exists(), "Could not find photo2.jpg");
        
        // Try to read the files to make sure they're valid
        try {
            byte[] photo1Bytes = Files.readAllBytes(Paths.get(workingPath));
            byte[] photo2Bytes = Files.readAllBytes(Paths.get(photo2Path));
            
            System.out.println("[DEBUG_LOG] Photo1 size: " + photo1Bytes.length + " bytes");
            System.out.println("[DEBUG_LOG] Photo2 size: " + photo2Bytes.length + " bytes");
            
            assertTrue(photo1Bytes.length > 0, "Photo1 is empty");
            assertTrue(photo2Bytes.length > 0, "Photo2 is empty");
        } catch (IOException e) {
            fail("Failed to read photo files: " + e.getMessage());
        }
    }
}