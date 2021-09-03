package com.panda912.defensor.internal;

import android.widget.TextView;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/31 09:13
 */
public class TextViewDefensor {

  public static void setText(TextView textView, CharSequence text) {
    if (textView == null) {
      String error = "TextView.setText(CharSequence text) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      textView.setText(text);
    }
  }

  public static void setText(TextView textView, int resId) {
    if (textView == null) {
      String error = "TextView.setText(int resId) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      textView.setText(resId);
    }
  }

  public static void setText(TextView textView, int resId, TextView.BufferType type) {
    if (textView == null) {
      String error = "TextView.setText(int resId, BufferType type) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      textView.setText(resId, type);
    }
  }

  public static void setText(TextView textView, char[] text, int start, int len) {
    if (textView == null) {
      String error = "TextView.setText(char[] text, int start, int len) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      try {
        textView.setText(text, start, len);
      } catch (NullPointerException e) {
        String error = "TextView.setText(char[] text, int start, int len) throw npe, due to char array is null";
        CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
      } catch (IndexOutOfBoundsException e) {
        String error = "TextView.setText(char[] text, int start, int len) throw IndexOutOfBoundsException, " +
            "start is " + start + ", len is " + len + ", char array len is " + text.length;
        CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
      }
    }
  }

  public static void setHint(TextView textView, CharSequence hint) {
    if (textView == null) {
      String error = "TextView.setHint(CharSequence hint) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      textView.setHint(hint);
    }
  }

  public final void setHint(TextView textView, int resId) {
    if (textView == null) {
      String error = "TextView.setHint(int resId) throw npe, due to textView is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      textView.setHint(resId);
    }
  }

}
