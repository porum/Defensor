package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/17 13:40
 */
public class PrimitiveTypeParser {

  public static byte parseByte(String str) {
    return parseByte(str, false);
  }

  public static byte parseByteThrow(String str) {
    return parseByte(str, true);
  }

  public static short parseShort(String str) {
    return parseShort(str, false);
  }

  public static short parseShortThrow(String str) {
    return parseShort(str, true);
  }

  public static int parseInt(String str) {
    return parseInt(str, false);
  }

  public static int parseIntThrow(String str) {
    return parseInt(str, true);
  }

  public static float parseFloat(String str) {
    return parseFloat(str, false);
  }

  public static float parseFloatThrow(String str) {
    return parseFloat(str, true);
  }

  public static long parseLong(String str) {
    return parseLong(str, false);
  }

  public static long parseLongThrow(String str) {
    return parseLong(str, true);
  }

  public static double parseDouble(String str) {
    return parseDouble(str, false);
  }

  public static double parseDoubleThrow(String str) {
    return parseDouble(str, true);
  }

  private static byte parseByte(String str, boolean isThrow) {
    try {
      return Byte.parseByte(str);
    } catch (NumberFormatException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Byte.parseByte(\"" + str + "\") throw IllegalArgumentException", e);
      if (isThrow) {
        throw e;
      }
    }
    return (byte) 0;
  }

  private static short parseShort(String str, boolean isThrow) {
    try {
      return Short.parseShort(str);
    } catch (NumberFormatException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Short.parseShort(\"" + str + "\") throw IllegalArgumentException", e);
      if (isThrow) {
        throw e;
      }
    }
    return (short) 0;
  }

  private static int parseInt(String str, boolean isThrow) {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Integer.parseInt(\"" + str + "\") throw IllegalArgumentException", e);
      if (isThrow) {
        throw e;
      }
    }
    return 0;
  }

  private static long parseLong(String str, boolean isThrow) {
    try {
      return Long.parseLong(str);
    } catch (NumberFormatException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Long.parseLong(\"" + str + "\") throw IllegalArgumentException", e);
      if (isThrow) {
        throw e;
      }
    }
    return 0;
  }

  private static float parseFloat(String str, boolean isThrow) {
    try {
      return Float.parseFloat(str);
    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Float.parseFloat(\"" + str + "\") throw NumberFormatException", e);
      } else if (e instanceof NullPointerException) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Float.parseFloat(\"" + str + "\") throw NullPointerException", e);
      }

      if (isThrow) {
        throw e;
      }
    }
    return (float) 0.0;
  }

  private static double parseDouble(String str, boolean isThrow) {
    try {
      return Double.parseDouble(str);
    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Double.parseDouble(\"" + str + "\") throw NumberFormatException", e);
      } else if (e instanceof NullPointerException) {
        CrashDefensor.onCrash(ErrorCode.NullPointerException, "Double.parseDouble(\"" + str + "\") throw NullPointerException", e);
      }

      if (isThrow) {
        throw e;
      }
    }
    return 0.0;
  }

}
