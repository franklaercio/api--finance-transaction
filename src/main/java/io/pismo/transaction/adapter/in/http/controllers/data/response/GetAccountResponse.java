package io.pismo.transaction.adapter.in.http.controllers.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccountResponse {

  @ApiModelProperty(example = "1")
  @JsonProperty("account_id")
  private Integer accountId;

  @ApiModelProperty(example = "12345678900")
  @JsonProperty("document_number")
  private String documentNumber;

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
