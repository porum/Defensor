package com.panda912.defensor.internal;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/23 13:31
 */
public class SafeDialog extends Dialog {

  public SafeDialog(@NonNull Context context) {
    super(context);
  }

  public SafeDialog(@NonNull Context context, int themeResId) {
    super(context, themeResId);
  }

  protected SafeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
  }

  @Override
  public void dismiss() {
    try {
      super.dismiss();
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "Dialog.dismiss() throw IllegalArgumentException", e);
    }
  }

  @Override
  public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
    try {
      return super.dispatchTouchEvent(ev);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "Dialog.dispatchTouchEvent() throw IllegalArgumentException", e);
      return true;
    }
  }

  @Override
  public void show() {
    try {
      super.show();
    } catch (WindowManager.BadTokenException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "Dialog.show() throw BadTokenException", e);
    } catch (WindowManager.InvalidDisplayException e) {
      CrashDefensor.onCrash(ErrorCode.DialogException, "Dialog.show() throw InvalidDisplayException", e);
    }
  }
}
