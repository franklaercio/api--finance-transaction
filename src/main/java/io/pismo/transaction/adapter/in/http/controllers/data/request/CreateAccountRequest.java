package io.pismo.transaction.adapter.in.http.controllers.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccountRequest {

  @ApiModelProperty(example = "12345678900")
  @JsonProperty("document_number")
  private String documentNumber;

  @ApiModelProperty(example = "12345678900")
  @JsonProperty("available_credit_limit")
  private BigDecimal availableCreditLimit;

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public BigDecimal getAvailableCreditLimit() {
    return availableCreditLimit;
  }

  public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
    this.availableCreditLimit = availableCreditLimit;
  }
}
