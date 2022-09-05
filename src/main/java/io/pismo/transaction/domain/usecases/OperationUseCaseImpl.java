package io.pismo.transaction.domain.usecases;

import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.port.in.OperationUseCase;
import io.pismo.transaction.domain.port.out.OperationDatabase;
import org.springframework.stereotype.Service;

@Service
public class OperationUseCaseImpl implements OperationUseCase {

  private final OperationDatabase operationDatabase;

  public OperationUseCaseImpl(OperationDatabase operationDatabase) {
    this.operationDatabase = operationDatabase;
  }

  @Override
  public Operation findOperationById(Long operationId) {
    return this.operationDatabase.findOperationById(operationId);
  }
}
