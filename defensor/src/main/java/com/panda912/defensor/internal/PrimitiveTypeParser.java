package com.panda912.defensor.internal;

import android.text.TextUtils;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/17 13:40
 */
public class PrimitiveTypeParser {

  public static short parseShort(String str) {
    return parseShort(str, (short) 0);
  }

  public static int parseInt(String str) {
    return parseInt(str, 0);
  }

  public static long parseLong(String str) {
    return parseLong(str, 0L);
  }

  public static float parseFloat(String str) {
    return parseFloat(str, 0.0f);
  }

  public static double parseDouble(String str) {
    return parseDouble(str, 0.0d);
  }

  public static byte parseByte(String str) {
    return parseByte(str, (byte) 0);
  }

  public static boolean parseBoolean(String str) {
    return parseBoolean(str, false);
  }

  public static short parseShort(String str, short defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Short.parseShort(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Short.parseShort() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static int parseInt(String str, int defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Integer.parseInt(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Integer.parseInt() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static long parseLong(String str, long defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Long.parseLong(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Long.parseLong() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static float parseFloat(String str, float defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Float.parseFloat(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Float.parseFloat() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static double parseDouble(String str, double defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Double.parseDouble(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Double.parseDouble() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static byte parseByte(String str, byte defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Byte.parseByte(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Byte.parseByte() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

  public static boolean parseBoolean(String str, boolean defValue) {
    if (!TextUtils.isEmpty(str)) {
      try {
        return Boolean.parseBoolean(str);
      } catch (NumberFormatException e) {
        CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Boolean.parseBoolean() throw IllegalArgumentException", e);
      }
    }
    return defValue;
  }

}
