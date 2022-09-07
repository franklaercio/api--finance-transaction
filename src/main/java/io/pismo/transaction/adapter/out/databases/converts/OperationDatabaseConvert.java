package io.pismo.transaction.adapter.out.databases.converts;

import io.pismo.transaction.adapter.out.databases.entities.OperationEntity;
import io.pismo.transaction.domain.models.Operation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationDatabaseConvert implements Converter<OperationEntity, Operation> {

  @Override
  public Operation convert(OperationEntity source) {
    return new Operation(source.getId(), source.getDescription());
  }
}
