package io.pismo.transaction.domain.port.out;

public interface AccountDatabase {

  void createAccount(String documentNumber);

}
