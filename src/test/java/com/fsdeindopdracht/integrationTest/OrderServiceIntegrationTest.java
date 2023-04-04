package com.fsdeindopdracht.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fsdeindopdracht.models.Authority;
import com.fsdeindopdracht.models.Order;
import com.fsdeindopdracht.models.Product;
import com.fsdeindopdracht.models.User;
import com.fsdeindopdracht.repositories.OrderRepository;
import com.fsdeindopdracht.repositories.ProductRepository;
import com.fsdeindopdracht.repositories.UserRepository;
import com.fsdeindopdracht.services.OrderService;
import com.fsdeindopdracht.services.ProductService;
import com.fsdeindopdracht.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class OrderServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    Order order1;
    Order order2;
    User user1;
    User user2;
    Product product1;
    Product product2;

    Set<Authority> authorities1 = new HashSet<>();
    Set<Authority> authorities2 = new HashSet<>();
    ArrayList<Product> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        if(orderRepository.count()>0) {
            orderRepository.deleteAll();
        }

        if(userRepository.count()>0) {
            userRepository.deleteAll();
        }

        if(productRepository.count()>0) {
            productRepository.deleteAll();
        }

        authorities1.add(new Authority("Kevin kostner", "ROLE_USER"));
        authorities2.add(new Authority("Michael jackson", "ROLE_USER"));

        user1 = new User(
                "Kevin kostner",
                "password1",
                null,
                authorities1,
                null
        );

        user2 = new User(
                "Michael jackson",
                "password2",
                null,
                authorities2,
                null
        );

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);


        product1 = new Product(
                102L,
                "apple",
                5.0,
                115.0,
                "fruit",
                null,
                null
        );

        product2 = new Product(
                103L,
                "carrot",
                2.55,
                45.0,
                "vegetable",
                null,
                null
        );

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);


        arrayList1.add(product1);
        arrayList1.add(product2);

        order1 = new Order(
                1L,
                123.0,
                LocalDate.now(),
                "25-03-2023",
                true,
                arrayList1,
                user1
        );

        order2 = new Order(
                2L,
                80.0,
                LocalDate.now(),
                "05-04-2023",
                true,
                arrayList1,
                user2
        );

        order1 = orderRepository.save(order1);
        order2 = orderRepository.save(order2);

        for(Product p : arrayList1) {
            arrayList2.add(asJsonString(p));
        }
    }



    @Test
    void shouldGetAllOrders() throws Exception {

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order1.getId().toString()))
                .andExpect(jsonPath("$[0].userName").value("Kevin kostner"))
                .andExpect(jsonPath("$[0].orderTotal").value(123.0))
                .andExpect(jsonPath("$[0].orderDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].pickUpDate").value("25-03-2023"))
                .andExpect(jsonPath("$[0].timeFrame").value(true))
                .andExpect(jsonPath("$[0].productNames[0].productName").value(("apple")))
                .andExpect(jsonPath("$[0].productNames[1].productName").value(("carrot")))

                .andExpect(jsonPath("$[0].id").value(order1.getId().toString()))
                .andExpect(jsonPath("$[1].userName").value("Michael jackson"))
                .andExpect(jsonPath("$[1].orderTotal").value(80.0))
                .andExpect(jsonPath("$[1].orderDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[1].pickUpDate").value("05-04-2023"))
                .andExpect(jsonPath("$[1].timeFrame").value(true))
                .andExpect(jsonPath("$[1].productNames[0].productName").value(("apple")))
                .andExpect(jsonPath("$[1].productNames[1].productName").value(("carrot")));

    }

    public static String asJsonString(final Product obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
