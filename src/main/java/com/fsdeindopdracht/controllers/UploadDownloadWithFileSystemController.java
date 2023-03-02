package com.fsdeindopdracht.controllers;


import com.fsdeindopdracht.models.FileUploadResponse;
import com.fsdeindopdracht.services.DatabaseService;
import com.fsdeindopdracht.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

@CrossOrigin
@RestController
public class UploadDownloadWithFileSystemController {
    private final FileStorageService fileStorageService;
    private final DatabaseService databaseService;

    public UploadDownloadWithFileSystemController(FileStorageService fileStorageService, DatabaseService databaseService) {
        this.fileStorageService = fileStorageService;
        this.databaseService = databaseService;
    }

    // PostMapping picture
    @PostMapping("/single/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){

        String fileName = fileStorageService.storeFile(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
        String contentType = file.getContentType();

        return new FileUploadResponse(fileName, contentType, url );
    }


    // GetMapping picture
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.downLoadFile(fileName);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }


}
