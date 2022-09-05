package io.pismo.transaction.domain.models;

public class Account {

  private Long accountId;
  private String documentNumber;

  public Account(Long accountId, String documentNumber) {
    this.accountId = accountId;
    this.documentNumber = documentNumber;
  }

  public Long getAccountId() {
    return accountId;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
