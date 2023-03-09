package com.fsdeindopdracht.services;

import com.fsdeindopdracht.execeptions.ProductNotFoundException;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.repositories.DocFileRepository;
import com.fsdeindopdracht.repositories.ProductRepository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@Service
public class DatabaseService {
    private final DocFileRepository doc;

    private final ProductRepository productRepository;

    public DatabaseService(DocFileRepository doc, ProductRepository productRepository){
        this.doc = doc;
        this.productRepository = productRepository;
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


    // Function for getMapping all files.
    public Collection<Image> getALlFromDB() {
        return doc.findAll();
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

}
