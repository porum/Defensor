package com.panda912.defensor.internal;

import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/10/8 10:00
 */
public class BottomSheetBehaviorDefensor {

  public static <V extends View> void setPeekHeight(BottomSheetBehavior<V> behavior, int peekHeight) {
    setPeekHeight(behavior, peekHeight, false);
  }

  public static <V extends View> void setPeekHeight(BottomSheetBehavior<V> behavior, int peekHeight, boolean animate) {
    if (behavior == null) {
      String err = "BottomSheetBehavior.setPeekHeight(" + peekHeight + ", " + animate + ") throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      behavior.setPeekHeight(peekHeight, animate);
    } catch (IllegalArgumentException e) {
      String err = "BottomSheetBehavior.setPeekHeight(" + peekHeight + ", " + animate + ") throw IllegalArgumentException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, e);
    }
  }

  public static <V extends View> void setState(BottomSheetBehavior<V> behavior, int state) {
    if (behavior == null) {
      String err = "BottomSheetBehavior.setState(" + state + ") throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      behavior.setState(state);
    } catch (IllegalArgumentException e) {
      String err = "BottomSheetBehavior.setState(" + state + ") throw IllegalArgumentException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, e);
    }
  }

  public static <V extends View> void setHalfExpandedRatio(BottomSheetBehavior<V> behavior, float ratio) {
    if (behavior == null) {
      String err = "BottomSheetBehavior.setHalfExpandedRatio(" + ratio + ") throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    behavior.setHalfExpandedRatio(Math.max(0, Math.min(1, ratio)));
    if ((ratio <= 0) || (ratio >= 1)) {
      String err = "BottomSheetBehavior.setHalfExpandedRatio(" + ratio + ") throw IllegalArgumentException, due to ratio must be a float value between 0 and 1.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
    }
  }

  public static <V extends View> void setExpandedOffset(BottomSheetBehavior<V> behavior, int offset) {
    if (behavior == null) {
      String err = "BottomSheetBehavior.setExpandedOffset(" + offset + ") throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    behavior.setExpandedOffset(Math.max(0, offset));
    if (offset < 0) {
      String err = "BottomSheetBehavior.setExpandedOffset(" + offset + ") throw IllegalArgumentException, due to offset must be greater than or equal to 0.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
    }
  }

}
