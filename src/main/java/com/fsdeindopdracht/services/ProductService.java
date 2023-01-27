package com.fsdeindopdracht.services;


import com.fsdeindopdracht.dtos.inputDto.ProductInputDto;
import com.fsdeindopdracht.dtos.outputDto.ProductOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    // Wrapper functie
    public Product transferInputDtoToProduct(ProductInputDto productInputDto) {

        Product newProduct = new Product();

        newProduct.setProductName(productInputDto.getProductName());
        newProduct.setPrice(productInputDto.getPrice());
        newProduct.setAvailableStock(productInputDto.getAvailableStock());
        newProduct.setCategory(productInputDto.getCategory());

        return productRepository.save(newProduct);
    }

    // Wrapper Functie
    public ProductOutputDto transferProductToOutputDto(Product product) {

        ProductOutputDto productOutputDto = new ProductOutputDto();

        productOutputDto.setProductName(product.getProductName());
        productOutputDto.setPrice(product.getPrice());
        productOutputDto.setAvailableStock(product.getAvailableStock());
        productOutputDto.setCategory(product.getCategory());

        return productOutputDto;
    }

    // Functie voor GetMapping van alle Products.
    public List<ProductOutputDto> getAllProducts() {
        List<Product> optionalProducts = productRepository.findAll();
        List<ProductOutputDto> ProductOutputDtoList = new ArrayList<>();

        if (optionalProducts.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            for (Product product : optionalProducts) {
                ProductOutputDto productOutputDto = transferProductToOutputDto(product);

                ProductOutputDtoList.add(productOutputDto);

            }
        }
        return ProductOutputDtoList;
    }

    // Functie voor getMapping één Product.
    public ProductOutputDto getProduct(Long id) {
        Optional<Product> requestedProduct = productRepository.findById(id);
        if (requestedProduct.isEmpty()) {
            throw new RecordNotFoundException("Record not found!");
        } else {
            return transferProductToOutputDto(requestedProduct.get());
        }
    }

    // Functie voor deleteMapping.
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        // optional tv.
        if (optionalProduct.isEmpty()) {
            throw new RecordNotFoundException("Television already removed or doesn't exist!");
            // throw exception.
        } else {
            Product ProductObj = optionalProduct.get();
            // er een tv van.
            productRepository.delete(ProductObj);
        }
    }

    // Functie voor PostMapping
    public Product createProduct(ProductInputDto productInputDto) {
        Product newProduct = transferInputDtoToProduct(productInputDto);
        return productRepository.save(newProduct);
    }


    // Functie voor PatchMapping.
    public ProductOutputDto updateProduct(Long id, ProductInputDto productInputDto) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {

            Product productUpdate = optionalProduct.get();

            if (productInputDto.getProductName() != null) {
                productUpdate.setProductName(productInputDto.getProductName());
            }
            if (productInputDto.getPrice() != null) {
                productUpdate.setPrice(productInputDto.getPrice());
            }
            if (productInputDto.getAvailableStock() != null) {
                productUpdate.setAvailableStock(productInputDto.getAvailableStock());
            }
            if (productInputDto.getCategory() != null) {
                productUpdate.setCategory(productInputDto.getCategory());
            }
            Product updatedProduct = productRepository.save(productUpdate);
            return transferProductToOutputDto(updatedProduct);
        } else {
            throw new RecordNotFoundException("Product not found!");

        }
    }
}


