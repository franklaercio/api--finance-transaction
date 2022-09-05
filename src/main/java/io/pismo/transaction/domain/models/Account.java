package io.pismo.transaction.domain.models;

public class Account {

  private int accountId;
  private String documentNumber;

  public Account(int accountId, String documentNumber) {
    this.accountId = accountId;
    this.documentNumber = documentNumber;
  }

  public int getAccountId() {
    return accountId;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
