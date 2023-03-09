package com.fsdeindopdracht.controllers;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.models.FileUploadResponse;
import com.fsdeindopdracht.services.DatabaseService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@CrossOrigin(origins="*")
@RestController
public class UploadDownloadWithDatabaseController {

    private final DatabaseService databaseService;

    public UploadDownloadWithDatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // PostMapping request for uploading a single picture into DB.
    @PostMapping("single/uploadDb/{productname}")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file,  @PathVariable String productname) throws IOException {

        Image image = databaseService.uploadFileDocument(file, productname);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponse(image.getFileName(), url, contentType );
    }

}
