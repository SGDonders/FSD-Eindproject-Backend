package com.fsdeindopdracht.controllers;

import com.fsdeindopdracht.dtos.inputDto.ProductInputDto;
import com.fsdeindopdracht.dtos.outputDto.ProductOutputDto;
import com.fsdeindopdracht.execeptions.HandleException;
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // GetMapping request product.
    @GetMapping("{productname}")
    public ResponseEntity<ProductOutputDto> getProduct(@PathVariable String productname) {
        return ResponseEntity.ok(productService.getProduct(productname));
    }


    // GetMapping request all products.
    @GetMapping("")
    public ResponseEntity<List<ProductOutputDto>> getAllProducts() {
        List<ProductOutputDto> products = productService.getAllProducts();

        products.sort(Comparator.comparing(ProductOutputDto::getId));

        Long currentId = 1L;
        for (ProductOutputDto product : products) {
            if (!Objects.equals(product.getId(), currentId)) {
                product.setId(currentId);
            }
            currentId++;
        }
        return ResponseEntity.ok(products);
    }


    // PostMapping request product.
    @PostMapping("")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductInputDto productInputDto,
                                                BindingResult bindingResult) throws ValidationException {
        Utils.reportErrors(bindingResult);

        try {
            Product savedProduct = productService.createProduct(productInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath
                    ().path("/Product/" + savedProduct.getProductName()).toUriString());

            return ResponseEntity.created(uri).body(savedProduct);
        } catch (Exception ex) {
            throw new HandleException();
        }
    }


    // PatchMapping request product.
    @PatchMapping("/{productName}")
    public ResponseEntity<Object> updateProduct(@PathVariable String productName, @RequestBody ProductInputDto productInputDto) {
        ProductOutputDto productOutputDto = productService.updateProduct(productName, productInputDto);
        return ResponseEntity.ok().body(productOutputDto);
    }


    // DeleteMapping request product.
    @DeleteMapping("{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName) {
        productService.deleteProduct(productName); //
        return ResponseEntity.ok("Product successfull deleted");
    }
}
