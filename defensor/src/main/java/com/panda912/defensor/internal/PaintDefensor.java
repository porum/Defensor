package com.panda912.defensor.internal;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/6 17:25
 */
public class PaintDefensor {

  public static float measureText(Paint paint, char[] text, int index, int count) {
    if (paint == null) {
      String error = "Paint.measureText(char[] text, int index, int count) throw NullPointerException, due to paint is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0f;
    }
    try {
      return paint.measureText(text, index, count);
    } catch (IllegalArgumentException e) {
      String error = "Paint.measureText(char[] text, int index, int count) throw IllegalArgumentException, due to text is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, e);
    } catch (ArrayIndexOutOfBoundsException e) {
      String error = "Paint.measureText(char[] text, int index, int count) throw IndexOutOfBoundsException, index " + index + " count " + count + " length " + text.length;
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
    return 0f;
  }

  public static float measureText(Paint paint, String text, int start, int end) {
    if (paint == null) {
      String error = "Paint.measureText(String text, int start, int end) throw NullPointerException, due to paint is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0f;
    }
    try {
      return paint.measureText(text, start, end);
    } catch (IllegalArgumentException e) {
      String error = "Paint.measureText(String text, int start, int end) throw IllegalArgumentException, due to text is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, e);
    } catch (IndexOutOfBoundsException e) {
      String error = "Paint.measureText(String text, int start, int end) throw IndexOutOfBoundsException, start " + start + " end " + end + " length " + text.length();
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
    return 0f;
  }

  public static float measureText(Paint paint, String text) {
    if (paint == null) {
      String error = "Paint.measureText(String text) throw NullPointerException, due to paint is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0f;
    }
    if (text == null) {
      String error = "Paint.measureText(String text) throw IllegalArgumentException, due to text is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
      return 0f;
    }
    return paint.measureText(text);
  }

  public static float measureText(Paint paint, CharSequence text, int start, int end) {
    if (paint == null) {
      String error = "Paint.measureText(CharSequence text, int start, int end) throw NullPointerException, due to paint is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0f;
    }
    try {
      return paint.measureText(text, start, end);
    } catch (IllegalArgumentException e) {
      String error = "Paint.measureText(CharSequence text, int start, int end) throw IllegalArgumentException, due to text is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, e);
    } catch (IndexOutOfBoundsException e) {
      String error = "Paint.measureText(CharSequence text, int start, int end) throw IndexOutOfBoundsException, start " + start + " end " + end + " length " + text.length();
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
    return 0f;
  }

  public void getTextBounds(Paint paint, String text, int start, int end, Rect bounds) {
    if (paint == null || bounds == null) {
      String error = "Paint.getTextBounds(String text, int start, int end, Rect bounds) throw NullPointerException, due to " + (paint == null ? "paint" : "bounds") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      paint.getTextBounds(text, start, end, bounds);
    } catch (IndexOutOfBoundsException e) {
      String error = "Paint.getTextBounds(String text, int start, int end, Rect bounds) throw IndexOutOfBoundsException, start " + start + " end " + end + " length " + text.length();
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  public void getTextBounds(Paint paint, CharSequence text, int start, int end, Rect bounds) {
    if (paint == null || bounds == null) {
      String error = "Paint.getTextBounds(CharSequence text, int start, int end, Rect bounds) throw NullPointerException, due to " + (paint == null ? "paint" : "bounds") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      paint.getTextBounds(text, start, end, bounds);
    } catch (IndexOutOfBoundsException e) {
      String error = "Paint.getTextBounds(CharSequence text, int start, int end, Rect bounds) throw IndexOutOfBoundsException, start " + start + " end " + end + " length " + text.length();
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
  }

  public void getTextBounds(Paint paint, char[] text, int index, int count, Rect bounds) {
    if (paint == null || bounds == null) {
      String error = "Paint.getTextBounds(char[] text, int start, int end, Rect bounds) throw NullPointerException, due to " + (paint == null ? "paint" : "bounds") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      paint.getTextBounds(text, index, count, bounds);
    } catch (ArrayIndexOutOfBoundsException e) {
      String error = "Paint.getTextBounds(char[] text, int start, int end, Rect bounds) throw IndexOutOfBoundsException, start " + index + " count " + count + " length " + text.length;
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
  }

}
