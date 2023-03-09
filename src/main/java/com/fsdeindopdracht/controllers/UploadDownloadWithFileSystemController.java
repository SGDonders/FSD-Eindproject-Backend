package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.models.FileUploadResponse;
import com.fsdeindopdracht.services.DatabaseService;
import com.fsdeindopdracht.services.FileStorageService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
public class UploadDownloadWithFileSystemController {
    private final FileStorageService fileStorageService;
    private final DatabaseService databaseService;

    public UploadDownloadWithFileSystemController(FileStorageService fileStorageService, DatabaseService databaseService) {
        this.fileStorageService = fileStorageService;
        this.databaseService = databaseService;
    }

    // GetMapping request for downloading a picture from filesystem (File can be found in resource/upload file.)
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


    // PostMapping request for uploading a picture in to filesystem. (File is stored into resource/upload file.)
    @PostMapping("/single/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){

        String fileName = fileStorageService.storeFile(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
        String contentType = file.getContentType();

        return new FileUploadResponse(fileName, contentType, url );
    }



}
