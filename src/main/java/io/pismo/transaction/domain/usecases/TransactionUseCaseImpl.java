package io.pismo.transaction.domain.usecases;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.models.Transaction;
import io.pismo.transaction.domain.models.enums.OperationTypeEnum;
import io.pismo.transaction.domain.port.in.AccountUseCase;
import io.pismo.transaction.domain.port.in.OperationUseCase;
import io.pismo.transaction.domain.port.in.TransactionUseCase;
import io.pismo.transaction.domain.port.out.TransactionDatabase;
import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class TransactionUseCaseImpl implements TransactionUseCase {

  private final AccountUseCase accountUseCase;
  private final OperationUseCase operationUseCase;
  private final TransactionDatabase transactionDatabase;

  public TransactionUseCaseImpl(AccountUseCase accountUseCase, OperationUseCase operationUseCase,
      TransactionDatabase transactionDatabase) {
    this.accountUseCase = accountUseCase;
    this.operationUseCase = operationUseCase;
    this.transactionDatabase = transactionDatabase;
  }

  @Override
  public void createTransaction(Long accountId, Long operationId, BigDecimal amount) {

    if (Objects.isNull(accountId) || Objects.isNull(operationId) || Objects.isNull(amount)) {
      throw new BadRequestException("Please verify your request and try again");
    }

    Account account = this.accountUseCase.findAccountById(accountId);
    Operation operation = this.operationUseCase.findOperationById(operationId);

    if (isPayment(operation) && amount.compareTo(new BigDecimal("0.0")) < 0) {
      throw new BadRequestException("Please verify your request and try again");
    }

    if ((isWithDrawOrBuy(operation)) && amount.longValue() > 0) {
      amount = amount.negate();
    }

    this.transactionDatabase.createTransaction(
        new Transaction(account.getAccountId(), operation.getId(), amount));
  }

  private boolean isWithDrawOrBuy(Operation operation) {
    return operation.getDescription().equals(OperationTypeEnum.WITHDRAW.getValue())
        || operation.getDescription().equals(OperationTypeEnum.CASH_PURCHASE.getValue())
        || operation.getDescription().equals(OperationTypeEnum.PURCHASE_IN_INSTALLMENTS.getValue());
  }

  private boolean isPayment(Operation operation) {
    return operation.getDescription().equals(OperationTypeEnum.PAYMENT.getValue());
  }
}
