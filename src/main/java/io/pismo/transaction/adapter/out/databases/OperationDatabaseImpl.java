package io.pismo.transaction.adapter.out.databases;

import io.pismo.transaction.adapter.out.databases.converts.OperationDatabaseConvert;
import io.pismo.transaction.adapter.out.databases.entities.OperationEntity;
import io.pismo.transaction.adapter.out.databases.repositories.OperationRepository;
import io.pismo.transaction.domain.exceptions.NotFoundException;
import io.pismo.transaction.domain.models.Operation;
import io.pismo.transaction.domain.port.out.OperationDatabase;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class OperationDatabaseImpl implements OperationDatabase {

  private final OperationRepository operationRepository;
  private final OperationDatabaseConvert operationDatabaseConvert;

  public OperationDatabaseImpl(OperationRepository operationRepository,
      OperationDatabaseConvert operationDatabaseConvert) {
    this.operationRepository = operationRepository;
    this.operationDatabaseConvert = operationDatabaseConvert;
  }

  public Operation findOperationById(Long operationId) {
    OperationEntity operationEntity = this.operationRepository.findById(operationId).orElse(null);

    if(Objects.isNull(operationEntity)) {
      throw new NotFoundException("Operation not found, please check your request and try again");
    }

    return this.operationDatabaseConvert.convert(operationEntity);
  }
}
