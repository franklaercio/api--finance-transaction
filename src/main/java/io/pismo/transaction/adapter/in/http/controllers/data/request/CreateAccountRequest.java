package io.pismo.transaction.adapter.in.http.controllers.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccountRequest {

  @ApiModelProperty(example = "12345678900")
  @JsonProperty("document_number")
  private String documentNumber;

  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
