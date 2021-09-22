package com.panda912.defensor.internal;

import androidx.recyclerview.widget.GridLayoutManager;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/22 09:35
 */
public class GridLayoutManagerDefensor {

  public static void setStackFromEnd(GridLayoutManager manager, boolean stackFromEnd) {
    if (manager == null) {
      String msg = "GridLayoutManager.setStackFromEnd(boolean stackFromEnd) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    try {
      manager.setStackFromEnd(stackFromEnd);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, e.getMessage(), e);
    }
  }

  public static void setSpanCount(GridLayoutManager manager, int spanCount) {
    if (manager == null) {
      String msg = "GridLayoutManager.setSpanCount(int spanCount) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    try {
      manager.setSpanCount(spanCount);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, e.getMessage(), e);
    }
  }


}
