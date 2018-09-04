package org.yugzan.mongo.core;

import java.util.Arrays;
import java.util.Objects;
import org.yugzan.mongo.convert.StringEnumConvertable;

/**
 * @author yongzan
 * @date 2018/09/04
 */
public enum Status implements StringEnumConvertable {
  ACTIVE("active"), ONLINE("online"), OFFLINE("offline");

  private String value;

  private Status(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  /**
   *
   * @param value
   * @return
   * @throws NoSuchElementException
   */
  public static Status of(String value) {
    Objects.requireNonNull(value, "value is null");
    return Arrays.stream(Status.values()).filter(category -> category.value().equals(value))
        .findAny().get();
  }

}
