package io.pismo.transaction.domain.port.in;

import io.pismo.transaction.domain.models.Account;
import java.math.BigDecimal;

public interface AccountUseCase {

  Account createAccount(String documentNumber, BigDecimal availableCreditLimit);

  Account findAccountById(Long accountId);

  void updateAccount(Account account);

}
