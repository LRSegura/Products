package com.manage.product.purchase;

import com.manage.product.model.customer.Customer;
import com.manage.product.model.product.Product;
import com.manage.product.model.purchase.Purchase;
import com.manage.product.repository.customer.CustomerRepository;
import com.manage.product.repository.product.ProductRepository;
import com.manage.product.repository.purchase.PurchaseRepository;
import com.manage.product.service.purchase.PurchaseService;
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
public class WebServicePurchaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    @Order(1)
    void getAllPurchase() throws Exception {
        mvc.perform(get("/api/purchase/all"))
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
    void saveCustomer() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","cmpunk");
        jsonObject.put("discount",true);
        jsonObject.put("discountValue",BigDecimal.valueOf(30));
        String json = jsonObject.toString();

        mvc.perform(post("/api/customer").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void savePurchase() throws Exception {
        Product product =
                productRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));
        Customer customer =
                customerRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Customer does not exits"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idProduct",product.getId());
        jsonObject.put("idCustomer",customer.getId());
        jsonObject.put("quantity", 14);
        String json = jsonObject.toString();

        mvc.perform(post("/api/purchase").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(5)
    void getPurchase() throws Exception {
        Purchase purchase =
                purchaseRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Purchase does not exits"));
        mvc.perform(get("/api/purchase").queryParam("id", purchase.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    void updatePurchase() throws Exception {
        Purchase purchase =
                purchaseRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Purchase does not exits"));
        Product product =
                productRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Product does not exits"));
        Customer customer =
                customerRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException("Customer does not exits"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",purchase.getId());
        jsonObject.put("idProduct",product.getId());
        jsonObject.put("idCustomer",customer.getId());
        jsonObject.put("quantity",BigDecimal.valueOf(100));
        String json = jsonObject.toString();

        mvc.perform(put("/api/purchase").content(json)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(7)
    void deletePurchase() throws Exception {
        Purchase product =
                purchaseRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Purchase does not exits"));
        Long id = product.getId();
        mvc.perform(delete("/api/purchase").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    void deleteProduct() throws Exception {
        Product product =
                productRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Product does not exits"));
        Long id = product.getId();
        mvc.perform(delete("/api/product").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void deleteCustomer() throws Exception {
        Customer customer =
                customerRepository.findAllByOrderByRegisterDateDesc().stream().findFirst().orElseThrow(() -> new RuntimeException(
                        "Customer does not exits"));
        Long id = customer.getId();
        mvc.perform(delete("/api/customer").queryParam("id", id.toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
