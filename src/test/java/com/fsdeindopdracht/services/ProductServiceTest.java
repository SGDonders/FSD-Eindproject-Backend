package com.fsdeindopdracht.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fsdeindopdracht.dtos.inputDto.ProductInputDto;
import com.fsdeindopdracht.dtos.outputDto.ProductOutputDto;
import com.fsdeindopdracht.execeptions.RecordNotFoundException;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.repositories.ProductRepository;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @Test
    void testGetProduct() {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product1);
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);
        ProductOutputDto actualProduct = productService.getProduct("42");
        assertEquals(10.0d, actualProduct.getAvailableStock().doubleValue());
        assertEquals("Product Name", actualProduct.getProductName());
        assertEquals(10.0d, actualProduct.getPrice().doubleValue());
        Image expectedImage = actualProduct.image;
        assertSame(expectedImage, actualProduct.getImage());
        assertEquals(123L, actualProduct.getId().longValue());
        assertEquals("Category", actualProduct.getCategory());
        verify(productRepository).findByProductName((String) any());
    }


    @Test
    void testGetProduct2() {
        when(productRepository.findByProductName((String) any())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> productService.getProduct("42"));
        verify(productRepository).findByProductName((String) any());
    }


    @Test
    void testGetProduct3() {
        when(productRepository.findByProductName((String) any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));
        assertThrows(RecordNotFoundException.class, () -> productService.getProduct("42"));
        verify(productRepository).findByProductName((String) any());
    }


    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RecordNotFoundException.class, () -> productService.getAllProducts());
        verify(productRepository).findAll();
    }


    @Test
    void testGetAllProducts2() {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Products not found!");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Products not found!");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Products not found!");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Products not found!");

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product1);
        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(1, productService.getAllProducts().size());
        verify(productRepository).findAll();
    }


    @Test
    void testGetAllProducts3() {
        when(productRepository.findAll()).thenThrow(new RecordNotFoundException("An error occurred"));
        assertThrows(RecordNotFoundException.class, () -> productService.getAllProducts());
        verify(productRepository).findAll();
    }


    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(new Image());
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product1);

        Product product2 = new Product();
        product2.setAvailableStock(10.0d);
        product2.setCategory("Category");
        product2.setId(123L);
        product2.setImage(image1);
        product2.setOrders(new ArrayList<>());
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");
        when(productRepository.findMaxId()).thenReturn(123L);
        when(productRepository.save((Product) any())).thenReturn(product2);

        Image image2 = new Image();
        image2.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image2.setFileName("test.txt");
        image2.setId(123L);
        image2.setProduct(new Product());

        Product product3 = new Product();
        product3.setAvailableStock(10.0d);
        product3.setCategory("Category");
        product3.setId(123L);
        product3.setImage(image2);
        product3.setOrders(new ArrayList<>());
        product3.setPrice(10.0d);
        product3.setProductName("Product Name");

        Image image3 = new Image();
        image3.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image3.setFileName("test.txt");
        image3.setId(123L);
        image3.setProduct(product3);

        ProductInputDto productInputDto = new ProductInputDto();
        productInputDto.setAvailableStock(10.0d);
        productInputDto.setCategory("Category");
        productInputDto.setImage(image3);
        productInputDto.setPrice(10.0d);
        productInputDto.setProductName("Product Name");
        assertSame(product2, productService.createProduct(productInputDto));
        verify(productRepository).findMaxId();
        verify(productRepository).save((Product) any());
    }


    @Test
    void testCreateProduct2() {
        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(new Image());
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product1);

        Product product2 = new Product();
        product2.setAvailableStock(10.0d);
        product2.setCategory("Category");
        product2.setId(123L);
        product2.setImage(image1);
        product2.setOrders(new ArrayList<>());
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");
        when(productRepository.findMaxId()).thenReturn(null);
        when(productRepository.save((Product) any())).thenReturn(product2);

        Image image2 = new Image();
        image2.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image2.setFileName("test.txt");
        image2.setId(123L);
        image2.setProduct(new Product());

        Product product3 = new Product();
        product3.setAvailableStock(10.0d);
        product3.setCategory("Category");
        product3.setId(123L);
        product3.setImage(image2);
        product3.setOrders(new ArrayList<>());
        product3.setPrice(10.0d);
        product3.setProductName("Product Name");

        Image image3 = new Image();
        image3.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image3.setFileName("test.txt");
        image3.setId(123L);
        image3.setProduct(product3);

        ProductInputDto productInputDto = new ProductInputDto();
        productInputDto.setAvailableStock(10.0d);
        productInputDto.setCategory("Category");
        productInputDto.setImage(image3);
        productInputDto.setPrice(10.0d);
        productInputDto.setProductName("Product Name");
        assertSame(product2, productService.createProduct(productInputDto));
        verify(productRepository).findMaxId();
        verify(productRepository).save((Product) any());
    }


    @Test
    void testUpdateProduct() {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product1);

        Product product2 = new Product();
        product2.setAvailableStock(10.0d);
        product2.setCategory("Category");
        product2.setId(123L);
        product2.setImage(new Image());
        product2.setOrders(new ArrayList<>());
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");

        Image image2 = new Image();
        image2.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image2.setFileName("test.txt");
        image2.setId(123L);
        image2.setProduct(product2);

        Product product3 = new Product();
        product3.setAvailableStock(10.0d);
        product3.setCategory("Category");
        product3.setId(123L);
        product3.setImage(image2);
        product3.setOrders(new ArrayList<>());
        product3.setPrice(10.0d);
        product3.setProductName("Product Name");

        Image image3 = new Image();
        image3.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image3.setFileName("test.txt");
        image3.setId(123L);
        image3.setProduct(product3);

        Product product4 = new Product();
        product4.setAvailableStock(10.0d);
        product4.setCategory("Category");
        product4.setId(123L);
        product4.setImage(image3);
        product4.setOrders(new ArrayList<>());
        product4.setPrice(10.0d);
        product4.setProductName("Product Name");
        when(productRepository.save((Product) any())).thenReturn(product4);
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);

        Image image4 = new Image();
        image4.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image4.setFileName("test.txt");
        image4.setId(123L);
        image4.setProduct(new Product());

        Product product5 = new Product();
        product5.setAvailableStock(10.0d);
        product5.setCategory("Category");
        product5.setId(123L);
        product5.setImage(image4);
        product5.setOrders(new ArrayList<>());
        product5.setPrice(10.0d);
        product5.setProductName("Product Name");

        Image image5 = new Image();
        image5.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image5.setFileName("test.txt");
        image5.setId(123L);
        image5.setProduct(product5);

        ProductInputDto productInputDto = new ProductInputDto();
        productInputDto.setAvailableStock(10.0d);
        productInputDto.setCategory("Category");
        productInputDto.setImage(image5);
        productInputDto.setPrice(10.0d);
        productInputDto.setProductName("Product Name");
        ProductOutputDto actualUpdateProductResult = productService.updateProduct("42", productInputDto);
        assertEquals(10.0d, actualUpdateProductResult.getAvailableStock().doubleValue());
        assertEquals("Product Name", actualUpdateProductResult.getProductName());
        assertEquals(10.0d, actualUpdateProductResult.getPrice().doubleValue());
        Image expectedImage = actualUpdateProductResult.image;
        assertSame(expectedImage, actualUpdateProductResult.getImage());
        assertEquals(123L, actualUpdateProductResult.getId().longValue());
        assertEquals("Category", actualUpdateProductResult.getCategory());
        verify(productRepository).save((Product) any());
        verify(productRepository).findByProductName((String) any());
    }


    @Test
    void testUpdateProduct2() {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product1);
        when(productRepository.save((Product) any())).thenThrow(new RecordNotFoundException("An error occurred"));
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);

        Image image2 = new Image();
        image2.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image2.setFileName("test.txt");
        image2.setId(123L);
        image2.setProduct(new Product());

        Product product2 = new Product();
        product2.setAvailableStock(10.0d);
        product2.setCategory("Category");
        product2.setId(123L);
        product2.setImage(image2);
        product2.setOrders(new ArrayList<>());
        product2.setPrice(10.0d);
        product2.setProductName("Product Name");

        Image image3 = new Image();
        image3.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image3.setFileName("test.txt");
        image3.setId(123L);
        image3.setProduct(product2);

        ProductInputDto productInputDto = new ProductInputDto();
        productInputDto.setAvailableStock(10.0d);
        productInputDto.setCategory("Category");
        productInputDto.setImage(image3);
        productInputDto.setPrice(10.0d);
        productInputDto.setProductName("Product Name");
        assertThrows(RecordNotFoundException.class, () -> productService.updateProduct("42", productInputDto));
        verify(productRepository).save((Product) any());
        verify(productRepository).findByProductName((String) any());
    }


    @Test
    void testDeleteProduct() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product1);
        doNothing().when(productRepository).delete((Product) any());
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);
        productService.deleteProduct("42");
        verify(productRepository).findByProductName((String) any());
        verify(productRepository).delete((Product) any());
    }


    @Test
    void testDeleteProduct2() {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image.setFileName("test.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        image1.setFileName("test.txt");
        image1.setId(123L);
        image1.setProduct(product);

        Product product1 = new Product();
        product1.setAvailableStock(10.0d);
        product1.setCategory("Category");
        product1.setId(123L);
        product1.setImage(image1);
        product1.setOrders(new ArrayList<>());
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product1);
        doThrow(new RecordNotFoundException("An error occurred")).when(productRepository).delete((Product) any());
        when(productRepository.findByProductName((String) any())).thenReturn(ofResult);
        assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct("42"));
        verify(productRepository).findByProductName((String) any());
        verify(productRepository).delete((Product) any());
    }


    @Test
    void testDeleteProduct3() {
        doNothing().when(productRepository).delete((Product) any());
        when(productRepository.findByProductName((String) any())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> productService.deleteProduct("42"));
        verify(productRepository).findByProductName((String) any());
    }
}

