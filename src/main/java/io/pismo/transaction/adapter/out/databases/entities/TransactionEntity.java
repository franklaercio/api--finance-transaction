package io.pismo.transaction.adapter.out.databases.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TRANSACTION")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_generator")
  @SequenceGenerator(name = "transaction_generator", sequenceName = "transaction_seq", allocationSize = 1)
  private long id;

  @NotNull(message = "Account number cannot be null")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ACCOUNT_ID")
  private AccountEntity account;

  @NotNull(message = "Operation type cannot be null")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OPERATION_ID")
  private OperationEntity operation;

  @Column(name = "AMOUNT", precision = 2)
  private BigDecimal amount;

  @Timestamp
  @CreatedDate
  @Column(name = "EVENT_DATE")
  private LocalDateTime eventDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public void setAccount(AccountEntity account) {
    this.account = account;
  }

  public void setAccount(Long accountId) {
    AccountEntity accountEntity = new AccountEntity();
    accountEntity.setId(accountId);
    this.account = accountEntity;
  }

  public OperationEntity getOperation() {
    return operation;
  }

  public void setOperation(OperationEntity operation) {
    this.operation = operation;
  }

  public void setOperation(Long operationId) {
    OperationEntity operationEntity = new OperationEntity();
    operationEntity.setId(operationId);
    this.operation = operationEntity;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
