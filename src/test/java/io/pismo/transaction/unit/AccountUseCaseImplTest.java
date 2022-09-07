package io.pismo.transaction.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import io.pismo.transaction.domain.port.out.RedisCache;
import io.pismo.transaction.domain.usecases.AccountUseCaseImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountUseCaseImplTest {

  @InjectMocks
  AccountUseCaseImpl accountUseCase;

  @Mock
  RedisCache redisCache;

  @Mock
  AccountDatabase accountDatabase;

  @Test
  void should_find_account_by_id_with_no_cache() {
    when(redisCache.find(any(), any())).thenReturn(null);
    when(accountDatabase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));

    Account account = this.accountUseCase.findAccountById(1L);

    assertEquals(1L, account.getAccountId());
    assertEquals("12345678900", account.getDocumentNumber());

    verify(this.redisCache, times(1)).find(any(), any());
    verify(this.redisCache, times(1)).create(any(), any(), any());
    verify(this.accountDatabase, times(1)).findAccountById(any());
  }

  @Test
  void should_find_account_by_id_with_cache() {
    when(redisCache.find(any(), any())).thenReturn(Optional.of(
        "{\"accountId\":1,\"documentNumber\":\"12345678900\"}"));

    Account account = this.accountUseCase.findAccountById(1L);

    assertEquals(1L, account.getAccountId());
    assertEquals("12345678900", account.getDocumentNumber());

    verify(this.redisCache, times(1)).find(any(), any());
    verify(this.redisCache, never()).create(any(), any(), any());
    verify(this.accountDatabase, never()).findAccountById(any());
  }

  @Test
  void should_given_bad_request_if_account_is_blank() {
    try {
      this.accountUseCase.findAccountById(null);
    } catch (Exception e) {
      assertEquals("Account isn't valid, please check your data", e.getMessage());
    }

    verify(this.accountDatabase, never()).createAccount(any());
  }

  @Test
  void should_create_new_account() {
    assertDoesNotThrow(() -> this.accountUseCase.createAccount("12345678900"));
    verify(this.accountDatabase, times(1)).createAccount(any());
  }

  @Test
  void should_given_bad_request_if_document_number_is_null() {
    try {
      this.accountUseCase.createAccount(null);
    } catch (Exception e) {
      assertEquals("Account isn't valid, please check your data", e.getMessage());
    }

    verify(this.accountDatabase, never()).createAccount(any());
  }

  @Test
  void should_given_bad_request_if_document_number_is_smaller_that_eleven() {
    try {
      this.accountUseCase.createAccount("123");
    } catch (Exception e) {
      assertEquals("Account isn't valid, please check your data", e.getMessage());
    }

    verify(this.accountDatabase, never()).createAccount(any());
  }

  @Test
  void should_given_bad_request_if_document_number_with_invalid_number() {
    try {
      this.accountUseCase.createAccount("123a456a8c00");
    } catch (Exception e) {
      assertEquals("Account isn't valid, please check your data", e.getMessage());
    }

    verify(this.accountDatabase, never()).createAccount(any());
  }

}
