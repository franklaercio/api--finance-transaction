package io.pismo.transaction.adapter.out.databases;

import io.pismo.transaction.adapter.out.databases.entities.TransactionEntity;
import io.pismo.transaction.adapter.out.databases.repositories.TransactionRepository;
import io.pismo.transaction.domain.models.Transaction;
import io.pismo.transaction.domain.port.out.TransactionDatabase;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionDatabaseImpl implements TransactionDatabase {

  private final TransactionRepository transactionRepository;

  private final ReentrantLock lock = new ReentrantLock();

  public TransactionDatabaseImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createTransaction(Transaction transaction) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setAccount(transaction.getAccountId());
    transactionEntity.setOperation(transaction.getOperationId());
    transactionEntity.setAmount(transaction.getAmount());

    this.transactionRepository.save(transactionEntity);
  }
}
