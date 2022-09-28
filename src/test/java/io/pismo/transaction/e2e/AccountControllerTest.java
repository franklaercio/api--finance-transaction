package io.pismo.transaction.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.pismo.transaction.Application;
import io.pismo.transaction.adapter.in.http.advices.ApplicationAdvice;
import io.pismo.transaction.adapter.in.http.controllers.AccountController;
import io.pismo.transaction.adapter.in.http.controllers.TransactionController;
import io.pismo.transaction.adapter.out.databases.entities.AccountEntity;
import io.pismo.transaction.adapter.out.databases.repositories.AccountRepository;
import io.pismo.transaction.configs.properties.RedisProperties;
import io.pismo.transaction.domain.port.out.RedisCache;
import io.pismo.transaction.utils.ContainerUtil;
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

@DisplayName("AccountE2eTest")
@ContextConfiguration(classes = {Application.class, ContainerUtil.class, RedisProperties.class},
    initializers = {ConfigDataApplicationContextInitializer.class})
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {

  MockMvc mockMvc;

  @Autowired
  ApplicationAdvice applicationAdvice;

  @Autowired
  AccountController accountController;

  @Autowired
  TransactionController transactionController;

  @Autowired
  RedisCache redisCache;

  @Autowired
  AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(applicationAdvice, accountController,
        transactionController).build();
  }

  @Test
  void should_get_account_by_id_and_save_cache() throws Exception {

    this.mockMvc.perform(
            get("/accounts/{accountId}", 1)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpectAll(content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.account_id").value(1),
            jsonPath("$.document_number").value("12345678900"),
            jsonPath("$.available_credit_limit").value("450.0")
        );

    String cache = this.redisCache.find("ACCOUNT-", "1").orElse(null);

    assertEquals(
        "{\"accountId\":1,\"documentNumber\":\"12345678900\",\"accountAvailableLimit\":450.00}",
        cache);
  }

  @Test
  void should_given_not_found_when_not_exists_account() throws Exception {

    this.mockMvc.perform(
            get("/accounts/{accountId}", -1)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpectAll(content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.code").value(404),
            jsonPath("$.message").value(
                "Account not found, please check your request and try again")
        );
  }

  @Test
  void should_given_bad_request_when_not_send_account_id() throws Exception {

    this.mockMvc.perform(
            get("/accounts/a")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_save_a_new_account() throws Exception {

    this.mockMvc.perform(
            post("/accounts")
                .content("{\"document_number\": \"12345678700\", \"available_credit_limit\": 450.00}")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());

    AccountEntity account = this.accountRepository.findById(2L).orElse(null);

    assertNotNull(account);

    assertEquals(2L, account.getId());
    assertEquals("12345678700", account.getDocumentNumber());
  }

  @Test
  void should_given_bad_request_when_not_send_document_number() throws Exception {

    this.mockMvc.perform(
            post("/accounts")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
