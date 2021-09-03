package com.panda912.defensor;

/**
 * Created by panda on 2021/8/17 14:00
 */
public interface ICrashCaughtListener {
  void onCrashCaught(int code, String msg, Throwable th);
}