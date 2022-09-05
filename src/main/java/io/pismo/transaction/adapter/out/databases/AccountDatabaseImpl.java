package io.pismo.transaction.adapter.out.databases;

import io.pismo.transaction.adapter.out.databases.entities.AccountEntity;
import io.pismo.transaction.adapter.out.databases.repositories.AccountRepository;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import org.springframework.stereotype.Service;

@Service
public class AccountDatabaseImpl implements AccountDatabase {

  private final AccountRepository accountRepository;

  public AccountDatabaseImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void createAccount(String documentNumber) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setDocumentNumber(documentNumber);

    this.accountRepository.save(accountEntity);
  }

}
