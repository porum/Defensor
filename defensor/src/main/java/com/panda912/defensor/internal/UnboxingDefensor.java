package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/2 16:42
 */
public class UnboxingDefensor {

  public static short shortValue(Short sh) {
    if (sh != null) {
      return sh;
    }
    String error = "Short unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static int intValue(Integer num) {
    if (num != null) {
      return num;
    }
    String error = "Integer unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static long longValue(Long l) {
    if (l != null) {
      return l;
    }
    String error = "Long unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static float floatValue(Float f) {
    if (f != null) {
      return f;
    }
    String error = "Float unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0.0f;
  }

  public static double doubleValue(Double d) {
    if (d != null) {
      return d;
    }
    String error = "Double unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0.0d;
  }

  public static byte byteValue(Byte b) {
    if (b != null) {
      return b;
    }
    String error = "Byte unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static boolean booleanValue(Boolean bool) {
    if (bool != null) {
      return bool;
    }
    String error = "Boolean unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return false;
  }

  public static char charValue(Character ch) {
    if (ch != null) {
      return ch;
    }
    String error = "Character unboxing throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

}
