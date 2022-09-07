package io.pismo.transaction.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import io.pismo.transaction.domain.port.in.OperationUseCase;
import io.pismo.transaction.domain.port.out.TransactionDatabase;
import io.pismo.transaction.domain.usecases.TransactionUseCaseImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionUseCaseImplTest {

  @InjectMocks
  TransactionUseCaseImpl transactionUseCase;

  @Mock
  TransactionDatabase transactionDatabase;

  @Mock
  AccountUseCase accountUseCase;

  @Mock
  OperationUseCase operationUseCase;

  @Test
  void should_create_transaction() {
    when(accountUseCase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));
    when(operationUseCase.findOperationById(any())).thenReturn(
        new Operation(4L, "PAGAMENTO"));

    assertDoesNotThrow(
        () -> this.transactionUseCase.createTransaction(1L, 4L, new BigDecimal("123.00")));

    verify(this.transactionDatabase, times(1)).createTransaction(any());
  }

  @Test
  void should_give_bad_request_with_payment_not_valid() {
    when(accountUseCase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));
    when(operationUseCase.findOperationById(any())).thenReturn(
        new Operation(4L, "PAGAMENTO"));

    try {
      this.transactionUseCase.createTransaction(1L, 4L, new BigDecimal("-123.00"));
    } catch (Exception e) {
      assertEquals("Please verify your request and try again", e.getMessage());
    }

    verify(this.transactionDatabase, never()).createTransaction(any());
  }
}
