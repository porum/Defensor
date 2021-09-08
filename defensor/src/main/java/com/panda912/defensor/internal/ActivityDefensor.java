package com.panda912.defensor.internal;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/31 10:32
 */
public class ActivityDefensor {

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
    if (activity == null) {
      String err = "Activity.requestPermissions(String[] permissions, int requestCode) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      activity.requestPermissions(permissions, requestCode);
    } catch (IllegalArgumentException e) {
      String err = "Activity.requestPermissions(String[] permissions, int requestCode) throw IllegalArgumentException, due to requestCode < 0.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
    }
  }

  public static void finish(Activity activity) {
    if (activity != null) {
      activity.finish();
    } else {
      String err = "Activity.finish() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public static void finishAffinity(Activity activity) {
    if (activity != null) {
      activity.finishAffinity();
    } else {
      String err = "Activity.finishAffinity() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public static void finishAfterTransition(Activity activity) {
    if (activity != null) {
      activity.finishAfterTransition();
    } else {
      String err = "Activity.finishAfterTransition() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public static void finishActivity(Activity activity, int requestCode) {
    if (activity != null) {
      activity.finishActivity(requestCode);
    } else {
      String err = "Activity.finishActivity(int requestCode) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public static void finishAndRemoveTask(Activity activity) {
    if (activity != null) {
      activity.finishAndRemoveTask();
    } else {
      String err = "Activity.finishAndRemoveTask() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    }
  }

  public static boolean isTaskRoot(Activity activity) {
    if (activity != null) {
      return activity.isTaskRoot();
    }
    String err = "Activity.isTaskRoot() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean moveTaskToBack(Activity activity, boolean nonRoot) {
    if (activity != null) {
      return activity.moveTaskToBack(nonRoot);
    }
    String err = "Activity.moveTaskToBack(boolean nonRoot) throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isFinishing(Activity activity) {
    if (activity != null) {
      return activity.isFinishing();
    }
    String error = "Activity.isFinishing() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return true;
  }

  public static boolean isDestroyed(Activity activity) {
    if (activity != null) {
      return activity.isDestroyed();
    }
    String error = "Activity.isDestroyed() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return true;
  }

  public static boolean isChangingConfigurations(Activity activity) {
    if (activity != null) {
      return activity.isChangingConfigurations();
    }
    String error = "Activity.isChangingConfigurations() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return false;
  }

}
