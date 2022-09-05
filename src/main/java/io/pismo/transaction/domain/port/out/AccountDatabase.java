package io.pismo.transaction.domain.port.out;

import io.pismo.transaction.domain.models.Account;

public interface AccountDatabase {

  void createAccount(String documentNumber);

  Account findAccountById(Long accountId);

}
