package io.pismo.transaction.adapter.out.databases;

import io.pismo.transaction.adapter.out.databases.converts.GetAccountConvert;
import io.pismo.transaction.adapter.out.databases.entities.AccountEntity;
import io.pismo.transaction.adapter.out.databases.repositories.AccountRepository;
import io.pismo.transaction.domain.exceptions.NotFoundException;
import io.pismo.transaction.domain.models.Account;
import io.pismo.transaction.domain.port.out.AccountDatabase;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class AccountDatabaseImpl implements AccountDatabase {

  private final AccountRepository accountRepository;
  private final GetAccountConvert getAccountConvert;

  public AccountDatabaseImpl(AccountRepository accountRepository,
      GetAccountConvert getAccountConvert) {
    this.accountRepository = accountRepository;
    this.getAccountConvert = getAccountConvert;
  }

  public void createAccount(String documentNumber) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setDocumentNumber(documentNumber);

    this.accountRepository.save(accountEntity);
  }

  @Override
  public Account getAccountById(Integer accountId) {
    AccountEntity accountEntity = this.accountRepository.findById(accountId).orElse(null);

    if(Objects.isNull(accountEntity)) {
      throw new NotFoundException("Account not found, please check your request and try again");
    }

    return this.getAccountConvert.convert(accountEntity);
  }

}
