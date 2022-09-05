package io.pismo.transaction.domain.usecases;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import io.pismo.transaction.domain.models.Transaction;
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

    if(Objects.isNull(accountId) || Objects.isNull(operationId) || Objects.isNull(amount)) {
      throw new BadRequestException("Please verify your request and try again");
    }

    Transaction transaction = new Transaction();
    transaction.setAccountId(this.accountUseCase.findAccountById(accountId).getAccountId());
    transaction.setOperationId(this.operationUseCase.findOperationById(operationId).getId());
    transaction.setAmount(amount);

    this.transactionDatabase.createTransaction(transaction);
  }
}
