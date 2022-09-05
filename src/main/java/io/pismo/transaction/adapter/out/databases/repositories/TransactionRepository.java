package io.pismo.transaction.adapter.out.databases.repositories;

import io.pismo.transaction.adapter.out.databases.entities.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
