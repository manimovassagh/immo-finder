package com.github.manimovassagh.immo_finder.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PhotoFileTest {

    @TempDir
    Path tempDir;

    private Path photo1Path;
    private Path photo2Path;

    @BeforeEach
    void setUp() throws IOException {
        // Create test images in the temporary directory
        photo1Path = tempDir.resolve("photo1.jpg");
        photo2Path = tempDir.resolve("photo2.jpg");

        // Write some test data to the files
        Files.write(photo1Path, "test image 1 content".getBytes());
        Files.write(photo2Path, "test image 2 content".getBytes());

        System.out.println("[DEBUG_LOG] Created test images in: " + tempDir.toString());
    }

    @Test
    void testPhotoFilesExist() {
        // Verify the test images exist
        System.out.println("[DEBUG_LOG] Photo1 path: " + photo1Path.toString());
        System.out.println("[DEBUG_LOG] Photo1 exists: " + Files.exists(photo1Path));

        assertTrue(Files.exists(photo1Path), "Photo1 does not exist");

        System.out.println("[DEBUG_LOG] Photo2 path: " + photo2Path.toString());
        System.out.println("[DEBUG_LOG] Photo2 exists: " + Files.exists(photo2Path));

        assertTrue(Files.exists(photo2Path), "Photo2 does not exist");

        // Try to read the files to make sure they're valid
        try {
            byte[] photo1Bytes = Files.readAllBytes(photo1Path);
            byte[] photo2Bytes = Files.readAllBytes(photo2Path);

            System.out.println("[DEBUG_LOG] Photo1 size: " + photo1Bytes.length + " bytes");
            System.out.println("[DEBUG_LOG] Photo2 size: " + photo2Bytes.length + " bytes");

            assertTrue(photo1Bytes.length > 0, "Photo1 is empty");
            assertTrue(photo2Bytes.length > 0, "Photo2 is empty");
        } catch (IOException e) {
            fail("Failed to read photo files: " + e.getMessage());
        }
    }
}
