package com.panda912.defensor.internal;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/10/8 10:00
 */
public class RecyclerViewDefensor {

  public static void bindViewToPosition(RecyclerView.Recycler recycler, View view, int position) {
    try {
      recycler.bindViewToPosition(view, position);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "RecyclerView$Recycler.bindViewToPosition(view," + position + ") throw IndexOutOfBoundsException", e);
    }
  }

  public static View getViewForPosition(RecyclerView.Recycler recycler, int position) {
    try {
      return recycler.getViewForPosition(position);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "RecyclerView$Recycler.getViewForPosition(" + position + ") throw IndexOutOfBoundsException", e);
      return null;
    }
  }

  public static int convertPreLayoutPositionToPostLayout(RecyclerView.Recycler recycler, int position) {
    try {
      return recycler.convertPreLayoutPositionToPostLayout(position);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "RecyclerView$Recycler.convertPreLayoutPositionToPostLayout(" + position + ") throw IndexOutOfBoundsException", e);
      return 0;
    }
  }

}
