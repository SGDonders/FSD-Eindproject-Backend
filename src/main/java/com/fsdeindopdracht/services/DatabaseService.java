package com.fsdeindopdracht.services;

import com.fsdeindopdracht.execeptions.ProductNotFoundException;
import com.fsdeindopdracht.models.FileUploadResponse;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.repositories.DocFileRepository;
import com.fsdeindopdracht.repositories.ProductRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DatabaseService {
    private final DocFileRepository doc;

    private final ProductRepository productRepository;

    public DatabaseService(DocFileRepository doc, ProductRepository productRepository){
        this.doc = doc;
        this.productRepository = productRepository;
    }


    // Function for getMapping all files.
    public Collection<Image> getALlFromDB() {
        return doc.findAll();
    }




    // Function for postMapping a file.
    public Image uploadFileDocument(MultipartFile file, String productName) throws IOException {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name " + productName));

        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Image image = new Image();
        image.setFileName(name);
        image.setDocFile(file.getBytes());
        image.setProduct(product);

        doc.save(image);

        return image;
    }





    // Function for getMapping one file.
    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request){

        Image document = doc.findByFileName(fileName);

    //    this mediaType decides witch type you accept if you only accept 1 type
    //    MediaType contentType = MediaType.IMAGE_JPEG;
    //    this is going to accept multiple types
        String mimeType = request.getServletContext().getMimeType(document.getFileName());

    //    for download attachment use next line
    //    return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
    //    for showing image in browser
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());
    }

    // Function for postMapping multiple files.
    public List<FileUploadResponse> createMultipleUpload(MultipartFile[] files){
        List<FileUploadResponse> uploadResponseList = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {

            String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Image image = new Image();
            image.setFileName(name);
            try {
                image.setDocFile(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.save(image);

    //     next line makes url. example "http://localhost:8080/download/naam.jpg"
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(name).toUriString();

            String contentType = file.getContentType();

            FileUploadResponse response = new FileUploadResponse(name, contentType, url);

            uploadResponseList.add(response);
        });
        return uploadResponseList;
    }

    //
    public void getZipDownload(String[] files, HttpServletResponse response) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.stream(files).forEach(file -> {
                try {
                    createZipEntry(file, zos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            zos.finish();

            response.setStatus(200);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
        }
    }

    public Resource downLoadFileDatabase(String fileName) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(fileName).toUriString();

        Resource resource;

        try {
            resource = new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public void createZipEntry(String file, ZipOutputStream zos) throws IOException {

        Resource resource = downLoadFileDatabase(file);
        ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
        try {
            zipEntry.setSize(resource.contentLength());
            zos.putNextEntry(zipEntry);

            StreamUtils.copy(resource.getInputStream(), zos);

            zos.closeEntry();
        } catch (IOException e) {
            System.out.println("some exception while zipping");
        }

    }
}
