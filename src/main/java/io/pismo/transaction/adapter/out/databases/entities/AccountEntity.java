package io.pismo.transaction.adapter.out.databases.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_generator")
  @SequenceGenerator(name = "accounts_generator", sequenceName = "accounts_seq", allocationSize = 1)
  private int id;

  @Column(name = "DOCUMENT_NUMBER", length = 11)
  private String documentNumber;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
