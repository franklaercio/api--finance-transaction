package io.pismo.transaction.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.pismo.transaction.Application;
import io.pismo.transaction.adapter.in.http.advices.AccountAdvice;
import io.pismo.transaction.adapter.in.http.controllers.impl.AccountControllerImpl;
import io.pismo.transaction.adapter.in.http.controllers.impl.TransactionControllerImpl;
import io.pismo.transaction.adapter.out.databases.entities.TransactionEntity;
import io.pismo.transaction.adapter.out.databases.repositories.TransactionRepository;
import io.pismo.transaction.configs.properties.RedisProperties;
import io.pismo.transaction.domain.port.out.RedisCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayName("AccountControllerTest")
@ContextConfiguration(classes = {Application.class, RedisProperties.class},
    initializers = {ConfigDataApplicationContextInitializer.class})
@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

  MockMvc mockMvc;

  @Autowired
  AccountAdvice accountAdvice;

  @Autowired
  AccountControllerImpl accountController;

  @Autowired
  TransactionControllerImpl transactionController;

  @Autowired
  RedisCache redisCache;

  @Autowired
  TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(accountAdvice, accountController,
        transactionController).build();
  }

  @Test
  void should__get_account_by_id_and_save_cache() throws Exception {

    this.mockMvc.perform(
            post("/transactions")
                .content("{"
                    + "  \"account_id\": 1,"
                    + "  \"amount\": 0.45,"
                    + "  \"operation_id\": 4"
                    + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());

    TransactionEntity transaction = this.transactionRepository.findById(2L).orElse(null);

    assertNotNull(transaction);

    assertEquals(2L, transaction.getId());
    assertEquals(0.45, transaction.getAmount().doubleValue());

    String accountCache = this.redisCache.find("ACCOUNT-", "1").orElse(null);
    String operationCache = this.redisCache.find("OPERATION-", "4").orElse(null);

    assertEquals("{\"accountId\":1,\"documentNumber\":\"12345678900\"}", accountCache);
    assertEquals("{\"id\":4,\"description\":\"COMPRA PAGAMENTO\"}", operationCache);
  }

  @Test
  void should_given_bad_request_when_not_send_payload() throws Exception {

    this.mockMvc.perform(
            post("/transactions")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_given_bad_request_when_payment_is_not_valid() throws Exception {

    this.mockMvc.perform(
            post("/transactions")
                .content("{"
                    + "  \"account_id\": 1,"
                    + "  \"amount\": -0.45,"
                    + "  \"operation_id\": 4"
                    + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpectAll(content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.code").value(400),
            jsonPath("$.message").value("Please verify your request and try again")
        );
  }

  @Test
  void should_given_created_when_payment_is_valid() throws Exception {

    this.mockMvc.perform(
            post("/transactions")
                .content("{"
                    + "  \"account_id\": 1,"
                    + "  \"amount\": -0.45,"
                    + "  \"operation_id\": 2"
                    + "}")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }
}
