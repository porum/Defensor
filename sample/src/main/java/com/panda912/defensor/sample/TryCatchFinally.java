package com.panda912.defensor.sample;

public class TryCatchFinally {

  public static int parseInttttt(String str, int defValue) {
    try {
      return Integer.parseInt(str);
    } catch (NullPointerException e) {
      System.err.println("str is null");
    } catch (NumberFormatException ex) {
      System.err.println("str is not number");
      throw new RuntimeException(ex);
    }
    return defValue;
  }

  public static int parseInttttttt(String str, int defValue) {
    try {
      return Integer.parseInt(str);
    } catch (IllegalArgumentException e) {
      System.err.println("str is invalid: " + str);
    }
    return defValue;
  }

  public static int parseIntttttttttt(String str, int defValue) {
    int value;
    try {
      value = Integer.parseInt(str);
    } finally {
      value = defValue;
    }
    return value;
  }


}
