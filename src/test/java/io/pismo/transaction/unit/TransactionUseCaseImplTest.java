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
import io.pismo.transaction.domain.models.enums.OperationTypeEnum;
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
  void should_create_transaction_with_purchase_in_installments() {
    when(accountUseCase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));
    when(operationUseCase.findOperationById(any())).thenReturn(
        new Operation(2L, OperationTypeEnum.PURCHASE_IN_INSTALLMENTS.getValue()));

    assertDoesNotThrow(
        () -> this.transactionUseCase.createTransaction(1L, 2L, new BigDecimal("123.00")));

    verify(this.transactionDatabase, times(1)).createTransaction(any());
  }

  @Test
  void should_create_transaction_cash_purchase() {
    when(accountUseCase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));
    when(operationUseCase.findOperationById(any())).thenReturn(
        new Operation(1L, OperationTypeEnum.CASH_PURCHASE.getValue()));

    assertDoesNotThrow(
        () -> this.transactionUseCase.createTransaction(1L, 2L, new BigDecimal("123.00")));

    verify(this.transactionDatabase, times(1)).createTransaction(any());
  }

  @Test
  void should_create_transaction_cash_withdraw() {
    when(accountUseCase.findAccountById(any())).thenReturn(new Account(1L, "12345678900"));
    when(operationUseCase.findOperationById(any())).thenReturn(
        new Operation(1L, OperationTypeEnum.WITHDRAW.getValue()));

    assertDoesNotThrow(
        () -> this.transactionUseCase.createTransaction(1L, 3L, new BigDecimal("123.00")));

    verify(this.transactionDatabase, times(1)).createTransaction(any());
  }

  @Test
  void should_give_bad_request_when_payment_is_not_valid() {
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

  @Test
  void should_give_bad_request_when_not_send_account_id() {
    try {
      this.transactionUseCase.createTransaction(null, 4L, new BigDecimal("-123.00"));
    } catch (Exception e) {
      assertEquals("Please verify your request and try again", e.getMessage());
    }

    verify(this.transactionDatabase, never()).createTransaction(any());
  }

  @Test
  void should_give_bad_request_when_not_send_operation_id() {
    try {
      this.transactionUseCase.createTransaction(1L, null, new BigDecimal("-123.00"));
    } catch (Exception e) {
      assertEquals("Please verify your request and try again", e.getMessage());
    }

    verify(this.transactionDatabase, never()).createTransaction(any());
  }

  @Test
  void should_give_bad_request_when_not_send_amount() {
    try {
      this.transactionUseCase.createTransaction(1L, 2L, null);
    } catch (Exception e) {
      assertEquals("Please verify your request and try again", e.getMessage());
    }

    verify(this.transactionDatabase, never()).createTransaction(any());
  }
}
