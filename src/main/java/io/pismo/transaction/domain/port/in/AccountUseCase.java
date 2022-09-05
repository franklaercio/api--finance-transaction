package io.pismo.transaction.domain.port.in;

import io.pismo.transaction.domain.models.Account;

public interface AccountUseCase {

  void createAccount(String documentNumber);

  Account findAccountById(Long accountId);

}
