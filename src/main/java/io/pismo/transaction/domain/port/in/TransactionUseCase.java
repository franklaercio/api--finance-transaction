package io.pismo.transaction.domain.port.in;

import java.math.BigDecimal;

public interface TransactionUseCase {

  void createTransaction(Long accountId, Long operationId, BigDecimal amount);

}
