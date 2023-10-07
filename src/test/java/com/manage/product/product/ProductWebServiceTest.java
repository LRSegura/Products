package com.manage.product.product;

import com.manage.product.model.product.Product;
import com.manage.product.repository.product.ProductRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductWebServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository repository;

    @Test
    @Order(1)
    void getAllProduct() throws Exception {
        mvc.perform(get("/api/product/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    void saveProduct() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","ProductX");
        jsonObject.put("description","DescriptionX");
        jsonObject.put("defaultPrice", BigDecimal.valueOf(100));
        String json = jsonObject.toString();

        mvc.perform(post("/api/product").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void getProduct() throws Exception {
        Product product =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));
        mvc.perform(get("/api/product").queryParam("id", product.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(5)
    void updateProduct() throws Exception {
        Product product =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",product.getId());
        jsonObject.put("name","ProductXX");
        jsonObject.put("description","DescriptionXX");
        jsonObject.put("defaultPrice",BigDecimal.valueOf(100));
        String json = jsonObject.toString();

        mvc.perform(put("/api/product").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(6)
    void deleteProduct() throws Exception {
        Product product =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));
        Long id = product.getId();
        mvc.perform(delete("/api/product").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
