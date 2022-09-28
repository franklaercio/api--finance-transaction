package io.pismo.transaction.adapter.out.databases;

import io.pismo.transaction.adapter.out.databases.entities.TransactionEntity;
import io.pismo.transaction.adapter.out.databases.repositories.TransactionRepository;
import io.pismo.transaction.domain.models.Transaction;
import io.pismo.transaction.domain.port.out.TransactionDatabase;
import org.springframework.stereotype.Service;

@Service
public class TransactionDatabaseImpl implements TransactionDatabase {

  private final TransactionRepository transactionRepository;
  
  public TransactionDatabaseImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void createTransaction(Transaction transaction) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setAccount(transaction.getAccountId());
    transactionEntity.setOperation(transaction.getOperationId());
    transactionEntity.setAmount(transaction.getAmount());

    this.transactionRepository.save(transactionEntity);
  }
}
