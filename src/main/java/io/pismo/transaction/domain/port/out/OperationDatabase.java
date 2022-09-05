package io.pismo.transaction.domain.port.out;

import io.pismo.transaction.domain.models.Operation;

public interface OperationDatabase {

  Operation findOperationById(Long operationId);

}
