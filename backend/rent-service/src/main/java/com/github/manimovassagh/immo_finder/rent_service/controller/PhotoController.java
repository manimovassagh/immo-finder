package com.github.manimovassagh.immo_finder.rent_service.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.manimovassagh.immo_finder.rent_service.service.PhotoService;
import com.github.manimovassagh.immo_finder.rent_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/apartments/{apartmentId}/photos")
@Tag(name = "Apartment Photo Management", description = "APIs for managing apartment photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_user')")
    @Operation(
        summary = "Upload one or more photos for an apartment",
        description = "Uploads photos and associates them with the specified apartment. The user must own the apartment."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Photos uploaded successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data (e.g., apartment not found, no files, or some files empty)", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission to add photos to this apartment", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error (e.g., failed to save file)", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<String>>> uploadPhotos(
            @Parameter(description = "ID of the apartment to add photo to", required = true) @PathVariable UUID apartmentId,
            @Parameter(description = "The photo files to upload", required = true) @RequestParam("photos") List<MultipartFile> photos) {
        try {
            if (photos.isEmpty() || photos.stream().allMatch(MultipartFile::isEmpty)) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", "Photo files cannot be empty.", null));
            }
            List<String> filePaths = photoService.savePhotos(apartmentId, photos);
            if (filePaths.isEmpty() && !photos.isEmpty()) {
                 return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", "All photo files provided were empty or invalid.", null));
            }
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Photos uploaded successfully.", filePaths));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("ERROR", e.getMessage(), null));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>("ERROR", e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("ERROR", "Failed to save photos: " + e.getMessage(), null));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("ERROR", "An unexpected error occurred: " + e.getMessage(), null));
        }
    }
} 