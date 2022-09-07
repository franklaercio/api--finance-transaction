package io.pismo.transaction.domain.models.enums;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import java.util.Arrays;

public enum OperationTypeEnum {
  CASH_PURCHASE("COMPRA A VISTA"),
  PURCHASE_IN_INSTALLMENTS("COMPRA PARCELADA"),
  WITHDRAW("SAQUE"),
  PAYMENT("PAGAMENTO");

  private final String value;

  OperationTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static OperationTypeEnum fromValue(String value) {
    return Arrays.stream(values())
        .filter(v -> v.getValue().equals(value))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(
            "Operation isn't valid, please check your data and contact us"));
  }
}
