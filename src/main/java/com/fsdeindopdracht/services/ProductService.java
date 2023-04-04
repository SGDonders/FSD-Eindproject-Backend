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


    // Function for getMapping a product.
    public ProductOutputDto getProduct(String id) {
        Optional<Product> requestedProduct = productRepository.findByProductName(id);
        if (requestedProduct.isEmpty()) {
            throw new RecordNotFoundException("Product not found!");
        } else {
            return transferProductToOutputDto(requestedProduct.get());
        }
    }


    // Function for getMapping all products.
    public List<ProductOutputDto> getAllProducts() {
        List<Product> optionalProducts = productRepository.findAll();
        List<ProductOutputDto> ProductOutputDtoList = new ArrayList<>();

        if (optionalProducts.isEmpty()) {
            throw new RecordNotFoundException("Products not found!");
        } else {
            for (Product product : optionalProducts) {
                ProductOutputDto productOutputDto = transferProductToOutputDto(product);

                ProductOutputDtoList.add(productOutputDto);
            }
        }
        return ProductOutputDtoList;
    }


    // Function for postMapping a product.
    public Product createProduct(ProductInputDto productInputDto) {


        Product newProduct = transferInputDtoToProduct(productInputDto);
        Long maxId = productRepository.findMaxId();
        if (maxId == null) {
            newProduct.setId(1L);
        } else {
            newProduct.setId(maxId + 1);
        }
        return productRepository.save(newProduct);
    }


    // Function for patchMapping a product.
    public ProductOutputDto updateProduct(String id, ProductInputDto productInputDto) {

        Optional<Product> optionalProduct = productRepository.findByProductName(id);

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


    // Function for deleteMapping a product.
    public void deleteProduct(String id) {
        Optional<Product> optionalProduct = productRepository.findByProductName(id);

        if (optionalProduct.isEmpty()) {
            throw new RecordNotFoundException("Product already removed or doesn't exist!");

        } else {
            Product ProductObj = optionalProduct.get();

            productRepository.delete(ProductObj);
        }
    }


//    // koppelfunctie voor product en image
//    public void saveProductWithImage(Product product, Image image) {
//        product.setImage(image);
//        image.setProduct(product);
//        productRepository.save(product);
//    }


    // Mapper method inputDto to product.
    public Product transferInputDtoToProduct(ProductInputDto productInputDto) {

        Product newProduct = new Product();

        newProduct.setProductName(productInputDto.getProductName());
        newProduct.setPrice(productInputDto.getPrice());
        newProduct.setAvailableStock(productInputDto.getAvailableStock());
        newProduct.setCategory(productInputDto.getCategory());
        newProduct.setImage(productInputDto.getImage());

        return newProduct;
    }


    // Mapper method product to outputDto.
    public ProductOutputDto transferProductToOutputDto(Product product) {

        ProductOutputDto productOutputDto = new ProductOutputDto();

        productOutputDto.setProductName(product.getProductName());
        productOutputDto.setId(product.getId());
        productOutputDto.setPrice(product.getPrice());
        productOutputDto.setAvailableStock(product.getAvailableStock());
        productOutputDto.setCategory(product.getCategory());
        productOutputDto.setImage(product.getImage());

        return productOutputDto;
    }
}


