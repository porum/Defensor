package com.panda912.defensor.internal;

import android.app.Activity;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/31 10:32
 */
public class ActivityDefensor {

  public static boolean isDestroyed(Activity activity) {
    if (activity == null) {
      String error = "Activity.isDestroyed() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return true;
    } else {
      return activity.isDestroyed();
    }
  }

  public static boolean isChangingConfigurations(Activity activity) {
    if (activity == null) {
      String error = "Activity.isChangingConfigurations() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    } else {
      return activity.isChangingConfigurations();
    }
  }

}
