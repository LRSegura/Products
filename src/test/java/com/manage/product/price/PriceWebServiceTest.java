package com.manage.product.price;

import com.manage.product.model.price.Price;
import com.manage.product.model.product.Product;
import com.manage.product.repository.price.PriceRepository;
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
public class PriceWebServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    @Order(1)
    void getAllPrice() throws Exception {
        mvc.perform(get("/api/price/all"))
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
    @Order(3)
    void savePrice() throws Exception {
        Product product =
                productRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startValue",91);
        jsonObject.put("endValue",100);
        jsonObject.put("price", BigDecimal.valueOf(100));
        jsonObject.put("idProduct", product.getId());
        String json = jsonObject.toString();

        mvc.perform(post("/api/price").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void getPrice() throws Exception {
        Price price =
                priceRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Price does not exits"));
        mvc.perform(get("/api/price").queryParam("id", price.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(5)
    void updatePrice() throws Exception {
        Price price =
                priceRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Price does not exits"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",price.getId());
        jsonObject.put("startValue",101);
        jsonObject.put("endValue",110);
        jsonObject.put("price", BigDecimal.valueOf(100));
        String json = jsonObject.toString();

        mvc.perform(put("/api/price").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(6)
    void deletePrice() throws Exception {
        Price price =
                priceRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Price does not exits"));
        Long id = price.getId();
        mvc.perform(delete("/api/price").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    void deleteProduct() throws Exception {
        Product product =
                productRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Product does not exits"));
        Long id = product.getId();
        mvc.perform(delete("/api/product").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
