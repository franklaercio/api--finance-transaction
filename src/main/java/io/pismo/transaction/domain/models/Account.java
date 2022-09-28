package io.pismo.transaction.domain.models;

import java.math.BigDecimal;

public class Account {

  private Long accountId;
  private String documentNumber;
  private BigDecimal accountAvailableLimit;

  public Account(Long accountId, String documentNumber, BigDecimal accountAvailableLimit) {
    this.accountId = accountId;
    this.documentNumber = documentNumber;
    this.accountAvailableLimit = accountAvailableLimit;
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

  public BigDecimal getAccountAvailableLimit() {
    return accountAvailableLimit;
  }

  public void setAccountAvailableLimit(BigDecimal accountAvailableLimit) {
    this.accountAvailableLimit = accountAvailableLimit;
  }
}
