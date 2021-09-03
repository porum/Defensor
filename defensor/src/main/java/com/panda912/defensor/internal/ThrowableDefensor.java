package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by panda on 2021/9/2 16:04
 */
public class ThrowableDefensor {

  public static String getMessage(Throwable throwable) {
    if (throwable != null) {
      String message = throwable.getMessage();
      return message != null ? message : "";
    }
    String error = "Throwable.getMessage() throw NullPointerException, due to throwable is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return "";
  }

  public static String toString(Throwable throwable) {
    if (throwable != null) {
      return throwable.toString();
    }
    String error = "Throwable.toString() throw NullPointerException, due to throwable is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return "";
  }

  public static void printStackTrace(Throwable throwable) {
    if (throwable != null) {
      throwable.printStackTrace();
      return;
    }
    String error = "Throwable.printStackTrace() throw NullPointerException, due to throwable is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
  }

  public static void printStackTrace(Throwable throwable, PrintStream s) {
    if (throwable == null || s == null) {
      String error = "Throwable.printStackTrace(PrintStream s) throw NullPointerException, due to " + (throwable == null ? "throwable" : "PrintStream") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    throwable.printStackTrace(s);
  }

  public static void printStackTrace(Throwable throwable, PrintWriter s) {
    if (throwable == null || s == null) {
      String error = "Throwable.printStackTrace(PrintWriter s) throw NullPointerException, due to " + (throwable == null ? "throwable" : "PrintWriter") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    throwable.printStackTrace(s);
  }

}
