package com.panda912.defensor.internal;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.lang.reflect.Field;

/**
 * Created by panda on 2020/9/18 18:33
 */
public class ToastCompat {

  private static Field mTNField;
  private static Field mHandlerField;

  static {
    try {
      mTNField = Toast.class.getDeclaredField("mTN");
      mTNField.setAccessible(true);
      mHandlerField = mTNField.getType().getDeclaredField("mHandler");
      mHandlerField.setAccessible(true);
    } catch (Throwable e) {
      CrashDefensor.onCrash(ErrorCode.ToastException, "reflect get Toast mTN or mHandler failed", e);
    }
  }

  private static void hook(Toast toast) {
    try {
      Object mTN = mTNField.get(toast);
      mHandlerField.set(mTN, new HandlerProxy((Handler) mHandlerField.get(mTN)));
    } catch (Throwable e) {
      CrashDefensor.onCrash(ErrorCode.ToastException, "hook Toast mHandler failed", e);
    }
  }

  private static class HandlerProxy extends Handler {

    private final Handler mHandler;

    HandlerProxy(Handler handler) {
      mHandler = handler;
    }

    @Override
    public void dispatchMessage(@NonNull Message msg) {
      try {
        super.dispatchMessage(msg);
      } catch (Exception e) {
        CrashDefensor.onCrash(ErrorCode.ToastException, "Android 7.1 BadTokenException OR other custom device has BadTokenException", e);
      }
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
      mHandler.handleMessage(msg);
    }
  }

  public static void show(Toast toast) {
    hook(toast);
    toast.show();
  }
}
