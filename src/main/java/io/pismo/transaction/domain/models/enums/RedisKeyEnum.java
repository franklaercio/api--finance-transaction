package io.pismo.transaction.domain.models.enums;

import io.pismo.transaction.domain.exceptions.BadRequestException;
import java.util.Arrays;

public enum RedisKeyEnum {
  CACHE_ACCOUNT("ACCOUNT-"),
  CACHE_OPERATION("OPERATION-");

  private final String value;

  RedisKeyEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static RedisKeyEnum fromValue(String value) {
    return Arrays.stream(values())
        .filter(v -> v.getValue().equals(value))
        .findFirst()
        .orElseThrow(() -> new BadRequestException(
            "Cache key isn't valid, please check your data and contact us"));
  }
}
