package io.pismo.transaction.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

  private Long accountId;

  private Long operationId;

  private BigDecimal amount;

  private LocalDateTime eventDate;

  public Transaction(Long accountId, Long operationId, BigDecimal amount) {
    this.accountId = accountId;
    this.operationId = operationId;
    this.amount = amount;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getOperationId() {
    return operationId;
  }

  public void setOperationId(Long operationId) {
    this.operationId = operationId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getEventDate() {
    return eventDate;
  }

  public void setEventDate(LocalDateTime eventDate) {
    this.eventDate = eventDate;
  }
}
