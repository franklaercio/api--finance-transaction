package io.pismo.transaction.utils;

public class StringUtils {

  private StringUtils() {

  }

  public static int length(CharSequence cs) {
    return cs == null ? 0 : cs.length();
  }

  public static boolean isBlank(CharSequence cs) {
    int strLen = length(cs);
    if (strLen != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean isNumber(String text) {
    try {
      Double.parseDouble(text);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  public static boolean isNotNumberOrBlank(String text) {
    return isBlank(text) || !isNumber(text);
  }

}
