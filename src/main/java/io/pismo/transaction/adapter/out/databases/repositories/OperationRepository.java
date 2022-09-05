package io.pismo.transaction.adapter.out.databases.repositories;

import io.pismo.transaction.adapter.out.databases.entities.OperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<OperationEntity, Long> {

}
