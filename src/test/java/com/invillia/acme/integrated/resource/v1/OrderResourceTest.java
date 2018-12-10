package com.invillia.acme.integrated.resource.v1;

import static com.invillia.acme.util.ObjectMapperUtil.serializeToString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.invillia.acme.InvilliaApplication;
import com.invillia.acme.integrated.OrderDataUtil;
import com.invillia.acme.model.entity.OrderPaymentEntity;
import com.invillia.acme.model.repository.OrderRepository;
import com.invillia.acme.resource.v1.domain.Order;
import com.invillia.acme.service.OrderPaymentService;
import java.util.Collections;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InvilliaApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class OrderResourceTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderPaymentService orderPaymentService;

  @Before
  public void setupOrders() {
    orderRepository.saveAll(OrderDataUtil.orderEntities());
  }

  @After
  public void clearOrders() {
    orderRepository.deleteAll();
  }

  @Test
  public void createOrderSuccess() throws Exception {
    mvc.perform(post("/v1/orders")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(serializeToString(
            Order.builder().address("Address Fourth").items(OrderDataUtil.orderItems()).build())))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  public void createOrderError() throws Exception {
    mvc.perform(post("/v1/orders")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(
            serializeToString(Order.builder().address("").items(Collections.emptySet()).build())))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getByParamsSuccess() throws Exception {
    mvc.perform(get("/v1/orders?address=First&status=PAYMENT_PENDING"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void getByParamsNoContent() throws Exception {
    mvc.perform(get("/v1/orders?address=Fourth&status=PAYMENT_PENDING"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }


}
