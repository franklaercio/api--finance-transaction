package io.pismo.transaction.adapter.in.http.controllers.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransactionRequest {

  @ApiModelProperty(example = "1")
  @JsonProperty("account_id")
  private Long acccountId;

  @ApiModelProperty(example = "4")
  @JsonProperty("operation_id")
  private Long operationId;

  @ApiModelProperty(example = "123.45")
  @JsonProperty("amount")
  private BigDecimal amount;

  public Long getAcccountId() {
    return acccountId;
  }

  public void setAcccountId(Long acccountId) {
    this.acccountId = acccountId;
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
}
