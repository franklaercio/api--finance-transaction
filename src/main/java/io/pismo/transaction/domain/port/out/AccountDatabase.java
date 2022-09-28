package io.pismo.transaction.domain.port.out;

import io.pismo.transaction.domain.models.Account;
import java.math.BigDecimal;

public interface AccountDatabase {

  Account createAccount(String documentNumber, BigDecimal availableCreditLimit);

  Account findAccountById(Long accountId);

  Account updateAccount(Account account);

}
