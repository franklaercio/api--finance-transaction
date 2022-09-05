package io.pismo.transaction.adapter.out.databases.repositories;

import io.pismo.transaction.adapter.out.databases.entities.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

}
