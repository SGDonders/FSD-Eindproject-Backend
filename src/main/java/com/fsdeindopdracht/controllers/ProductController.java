package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.ProductInputDto;
import com.fsdeindopdracht.dtos.outputDto.ProductOutputDto;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.services.ProductService;
import com.fsdeindopdracht.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // GetMapping request alle Products.
    @GetMapping("")
    public ResponseEntity<List<ProductOutputDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    // GetMapping request voor één Product.
    @GetMapping("{id}")
    public ResponseEntity<ProductOutputDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }


    // DeleteMapping request voor een Product.
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); //
        return ResponseEntity.noContent().build();
    }


    // PostMapping request voor een Product.
    @PostMapping("")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductInputDto productInputDto,
                                                 BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);

        Product savedProduct = productService.createProduct(productInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                ().path("/Product/" + savedProduct.getId()).toUriString());

        return ResponseEntity.created(uri).body(savedProduct);
    }


    // PutMapping voor een Product.
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductInputDto productInputDto) {
        ProductOutputDto productOutputDto = productService.updateProduct(id, productInputDto);
        return ResponseEntity.ok().body(productOutputDto);
    }
}
