package com.panda912.defensor.internal;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/27 15:33
 */
public class HandlerDefensor {

  public static boolean post(Handler handler, Runnable r) {
    if (handler == null) {
      String err = "Handler.post(r) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    if (r == null) {
      String err = "Handler.post(r) throw IllegalArgumentException due to r is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return false;
    }
    return handler.post(r);
  }

  public static boolean postDelayed(Handler handler, Runnable r, long delayMillis) {
    if (handler == null) {
      String err = "Handler.postDelayed(r, delayMillis) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    if (r == null) {
      String err = "Handler.postDelayed(r, delayMillis) throw IllegalArgumentException due to r is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return false;
    }
    return handler.postDelayed(r, delayMillis);
  }

  public static void removeCallbacks(Handler handler, Runnable r) {
    if (handler == null) {
      String err = "Handler.removeCallbacks(r) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (r == null) {
      String err = "Handler.removeCallbacks(r) throw IllegalArgumentException due to r is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    handler.removeCallbacks(r);
  }

  public static void removeCallbacks(Handler handler, Runnable r, Object token) {
    if (handler == null) {
      String err = "Handler.removeCallbacks(r, token) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (r == null) {
      String err = "Handler.removeCallbacks(r, token) throw IllegalArgumentException due to r is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    handler.removeCallbacks(r, token);
  }

  public static boolean sendMessage(Handler handler, Message msg) {
    if (handler == null) {
      String err = "Handler.sendMessage(msg) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    if (msg == null) {
      String err = "Handler.sendMessage(msg) throw IllegalArgumentException due to msg is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return false;
    }
    return handler.sendMessage(msg);
  }

  public static void removeMessages(Handler handler, int what) {
    if (handler == null) {
      String err = "Handler.removeMessages(what) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    handler.removeMessages(what);
  }

  public static void removeMessages(Handler handler, int what, Object object) {
    if (handler == null) {
      String err = "Handler.removeMessages(what, object) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    handler.removeMessages(what, object);
  }

  public static void removeCallbacksAndMessages(Handler handler, Object token) {
    if (handler == null) {
      String err = "Handler.removeCallbacksAndMessages(token) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    handler.removeCallbacksAndMessages(token);
  }

  public static boolean hasMessages(Handler handler, int what) {
    if (handler == null) {
      String err = "Handler.hasMessages(what) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    return handler.hasMessages(what);
  }

  public static boolean hasMessages(Handler handler, int what, Object object) {
    if (handler == null) {
      String err = "Handler.hasMessages(what, object) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    return handler.hasMessages(what, object);
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  public static boolean hasCallbacks(Handler handler, Runnable r) {
    if (handler == null) {
      String err = "Handler.hasCallbacks(r) throw NullPointerException due to handler is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return false;
    }
    if (r == null) {
      String err = "Handler.hasCallbacks(r) throw IllegalArgumentException due to r is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return false;
    }
    return handler.hasCallbacks(r);
  }
}
