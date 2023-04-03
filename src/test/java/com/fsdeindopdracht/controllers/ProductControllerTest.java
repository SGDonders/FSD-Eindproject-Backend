package com.fsdeindopdracht.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsdeindopdracht.dtos.inputDto.ProductInputDto;
import com.fsdeindopdracht.dtos.outputDto.ProductOutputDto;
import com.fsdeindopdracht.models.Image;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.services.ProductService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;


    @Test
    void testGetProduct() throws Exception {
        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(new Image());
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image.setFileName("foo.txt");
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
        image1.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileName("foo.txt");
        image1.setId(123L);
        image1.setProduct(product1);

        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.setAvailableStock(10.0d);
        productOutputDto.setCategory("Category");
        productOutputDto.setId(123L);
        productOutputDto.setImage(image1);
        productOutputDto.setPrice(10.0d);
        productOutputDto.setProductName("Product Name");
        when(productService.getProduct((String) any())).thenReturn(productOutputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/{productname}", "Productname");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productName\":\"Product Name\",\"id\":123,\"price\":10.0,\"availableStock\":10.0,\"category\":\"Category\",\"image"
                                        + "\":{\"id\":123,\"fileName\":\"foo.txt\",\"docFile\":\"QUFBQUFBQUE=\"}}"));
    }


    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllProducts2() throws Exception {
        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image.setFileName("foo.txt");
        image.setId(123L);
        image.setProduct(new Product());

        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("?");
        product.setId(123L);
        product.setImage(image);
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("?");

        Image image1 = new Image();
        image1.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileName("foo.txt");
        image1.setId(123L);
        image1.setProduct(product);

        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.setAvailableStock(10.0d);
        productOutputDto.setCategory("?");
        productOutputDto.setId(123L);
        productOutputDto.setImage(image1);
        productOutputDto.setPrice(10.0d);
        productOutputDto.setProductName("?");

        ArrayList<ProductOutputDto> productOutputDtoList = new ArrayList<>();
        productOutputDtoList.add(productOutputDto);
        when(productService.getAllProducts()).thenReturn(productOutputDtoList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"productName\":\"?\",\"id\":1,\"price\":10.0,\"availableStock\":10.0,\"category\":\"?\",\"image\":{\"id\":123,\"fileName"
                                        + "\":\"foo.txt\",\"docFile\":\"QUFBQUFBQUE=\"}}]"));
    }


    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setId(123L);
        product.setImage(new Image());
        product.setOrders(new ArrayList<>());
        product.setPrice(10.0d);
        product.setProductName("Product Name");

        Image image = new Image();
        image.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image.setFileName("foo.txt");
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
        image1.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileName("foo.txt");
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
        when(productService.createProduct((ProductInputDto) any())).thenReturn(product2);

        Product product3 = new Product();
        product3.setAvailableStock(10.0d);
        product3.setCategory("Category");
        product3.setId(123L);
        product3.setImage(new Image());
        product3.setOrders(new ArrayList<>());
        product3.setPrice(10.0d);
        product3.setProductName("Product Name");

        Image image2 = new Image();
        image2.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileName("foo.txt");
        image2.setId(123L);
        image2.setProduct(product3);

        Product product4 = new Product();
        product4.setAvailableStock(10.0d);
        product4.setCategory("Category");
        product4.setId(123L);
        product4.setImage(image2);
        product4.setOrders(new ArrayList<>());
        product4.setPrice(10.0d);
        product4.setProductName("Product Name");

        Image image3 = new Image();
        image3.setDocFile("AAAAAAAA".getBytes("UTF-8"));
        image3.setFileName("foo.txt");
        image3.setId(123L);
        image3.setProduct(product4);

        ProductInputDto productInputDto = new ProductInputDto();
        productInputDto.setAvailableStock(10.0d);
        productInputDto.setCategory("Category");
        productInputDto.setImage(image3);
        productInputDto.setPrice(10.0d);
        productInputDto.setProductName("Product Name");
        String content = (new ObjectMapper()).writeValueAsString(productInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"productName\":\"Product Name\",\"price\":10.0,\"availableStock\":10.0,\"category\":\"Category\",\"image"
                                        + "\":{\"id\":123,\"fileName\":\"foo.txt\",\"docFile\":\"QUFBQUFBQUE=\"}}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/Product/Product%20Name"));
    }


    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/{productName}",
                "Product Name");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Product successfull deleted"));
    }
}

