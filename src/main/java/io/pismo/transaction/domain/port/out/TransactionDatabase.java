package io.pismo.transaction.domain.port.out;

import io.pismo.transaction.domain.models.Transaction;

public interface TransactionDatabase {

  void createTransaction(Transaction transaction);

}
