package com.panda912.defensor.samplelib;

import android.util.Log;

public class Logger {
  public static void i(String tag, String msg) {
    Log.i(tag, msg);
  }

  public static void w(String tag, CharSequence msg) {
    Log.w(tag, msg.toString());
  }
}