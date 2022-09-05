package io.pismo.transaction.adapter.out.databases.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OPERATION")
public class OperationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_generator")
  @SequenceGenerator(name = "operation_generator", sequenceName = "operation_seq", allocationSize = 1)
  private long id;

  @Column(name = "DESCRIPTION")
  private String description;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
