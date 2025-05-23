package com.github.manimovassagh.immo_finder.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${photo.upload.dir:images}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        // Ensure the upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Created photo upload directory: {}", directory.getAbsolutePath());
            } else {
                logger.warn("Failed to create photo upload directory: {}", directory.getAbsolutePath());
            }
        } else {
            logger.info("Photo upload directory already exists: {}", directory.getAbsolutePath());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Get the absolute path to the upload directory
        Path uploadPath = Paths.get(uploadDir);
        String absolutePath = uploadPath.toFile().getAbsolutePath();

        logger.info("Configuring resource handler for photo uploads. Path: {}", absolutePath);

        // Register the resource handler for the upload directory
        registry.addResourceHandler("/" + uploadDir + "/**")
                .addResourceLocations("file:" + absolutePath + "/");

        logger.info("Resource handler configured: /{}//** -> file:{}/", uploadDir, absolutePath);
    }
}
