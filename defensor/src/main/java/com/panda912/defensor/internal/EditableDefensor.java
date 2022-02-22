package com.panda912.defensor.internal;

import android.text.Editable;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2022/2/22 15:22
 */
public class EditableDefensor {

  public static Editable replace(Editable editable, int st, int en, CharSequence source, int start, int end) {
    try {
      return editable.replace(st, en, source, start, end);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.replace(int st, int en, CharSequence source, int start, int end) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable replace(Editable editable, int st, int en, CharSequence text) {
    try {
      return editable.replace(st, en, text);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.replace(int st, int en, CharSequence text) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable insert(Editable editable, int where, CharSequence text, int start, int end) {
    try {
      return editable.insert(where, text, start, end);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.insert(int where, CharSequence text, int start, int end) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable insert(Editable editable, int where, CharSequence text) {
    try {
      return editable.insert(where, text);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.insert(int where, CharSequence text) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable delete(Editable editable, int st, int en) {
    try {
      return editable.delete(st, en);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.delete(int st, int en) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable append(Editable editable, CharSequence text) {
    try {
      return editable.append(text);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.append(CharSequence text) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable append(Editable editable, CharSequence text, int start, int end) {
    try {
      return editable.append(text, start, end);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.append(CharSequence text, int start, int end) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

  public static Editable append(Editable editable, char text) {
    try {
      return editable.append(text);
    } catch (IndexOutOfBoundsException e) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Editable.append(char text) throw IndexOutOfBoundsException", e);
    }
    return editable;
  }

}
