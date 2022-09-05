package io.pismo.transaction.domain.port.in;

import io.pismo.transaction.domain.models.Operation;

public interface OperationUseCase {

  Operation findOperationById(Long operationId);

}
