package com.manage.product.customer;

import com.manage.product.model.customer.Customer;
import com.manage.product.repository.customer.CustomerRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerWebServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository repository;

    @Test
    @Order(1)
    void getAllCustomer() throws Exception {
        mvc.perform(get("/api/customer/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    void saveCustomer() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","cmpunk");
        jsonObject.put("discount",false);
        jsonObject.put("discountValue",BigDecimal.ZERO);
        String json = jsonObject.toString();

        mvc.perform(post("/api/customer").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    void getCustomer() throws Exception {
        Customer customer =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Customer does not exits"));
        mvc.perform(get("/api/customer").queryParam("id", customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(4)
    void updateCustomer() throws Exception {
        Customer customer =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Customer does not exits"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",customer.getId());
        jsonObject.put("name","cmpunk");
        jsonObject.put("discount",true);
        jsonObject.put("discountValue",BigDecimal.valueOf(50));
        String json = jsonObject.toString();

        mvc.perform(put("/api/customer").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(5)
    void deleteCustomer() throws Exception {
        Customer customer =
                repository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Customer does not exits"));
        Long id = customer.getId();
        mvc.perform(delete("/api/customer").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
