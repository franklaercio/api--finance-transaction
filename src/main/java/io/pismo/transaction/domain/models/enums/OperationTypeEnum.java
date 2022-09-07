package io.pismo.transaction.domain.models.enums;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import java.util.Arrays;

public enum OperationTypeEnum {
  COMPRA_VISTA("COMPRA A VISTA"),
  COMPRA_PARCELADA("COMPRA PARCELADA"),
  COMPRA_SAQUE("COMPRA SAQUE"),
  COMPRA_PAGAMENTO("COMPRA PAGAMENTO");

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
