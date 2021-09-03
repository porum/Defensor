package com.panda912.defensor.internal;

import android.content.SharedPreferences;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/19 13:52
 */
public class NullPointerExceptionDefensor {

  public static int hashCode(Object obj) {
    if (obj != null) {
      return obj.hashCode();
    }
    CrashDefensor.onCrash(ErrorCode.NullPointerException, "Object.hashCode() throw NullPointerException", new NullPointerException("Object.hashCode() throw NullPointerException"));
    return 0;
  }

  public static String getString(SharedPreferences sharedPreferences, String key, String defVal) {
    if (sharedPreferences != null) {
      return sharedPreferences.getString(key, defVal);
    }
    CrashDefensor.onCrash(ErrorCode.NullPointerException, "SharedPreferences.getString(String key, String defVal) throw npe, due to sp is null", new NullPointerException("SharedPreferences.getString(String key, String defVal) throw npe, due to sp is null"));
    return defVal;
  }

}
