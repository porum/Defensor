package com.panda912.defensor.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/7 17:30
 */
public class FragmentDefensor {

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static Context getContext(Fragment fragment) {
    if (fragment != null) {
      return fragment.getContext();
    }
    String err = "Fragment.getContext() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return null;
  }

  public static Activity getActivity(Fragment fragment) {
    if (fragment != null) {
      return fragment.getActivity();
    }
    String err = "Fragment.getActivity() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return null;
  }

  public static boolean isAdded(Fragment fragment) {
    if (fragment != null) {
      return fragment.isAdded();
    }
    String err = "Fragment.isAdded() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isDetached(Fragment fragment) {
    if (fragment != null) {
      return fragment.isDetached();
    }
    String err = "Fragment.isDetached() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isRemoving(Fragment fragment) {
    if (fragment != null) {
      return fragment.isRemoving();
    }
    String err = "Fragment.isRemoving() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isInLayout(Fragment fragment) {
    if (fragment != null) {
      return fragment.isInLayout();
    }
    String err = "Fragment.isInLayout() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isResumed(Fragment fragment) {
    if (fragment != null) {
      return fragment.isResumed();
    }
    String err = "Fragment.isResumed() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isVisible(Fragment fragment) {
    if (fragment != null) {
      return fragment.isVisible();
    }
    String err = "Fragment.isVisible() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static boolean isHidden(Fragment fragment) {
    if (fragment != null) {
      return fragment.isHidden();
    }
    String err = "Fragment.isHidden() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return false;
  }

  public static void startActivity(Fragment fragment, Intent intent) {
    if (fragment == null) {
      String err = "Fragment.startActivity(Intent intent) throw NullPointerException, due to fragment is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (intent == null) {
      String err = "Fragment.startActivity(Intent intent) throw IllegalArgumentException, due to intent is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    try {
      fragment.startActivity(intent);
    } catch (IllegalStateException e) {
      String err = "Fragment.startActivity(Intent intent) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static void startActivity(Fragment fragment, Intent intent, Bundle options) {
    if (fragment == null) {
      String err = "Fragment.startActivity(Intent intent, Bundle options) throw NullPointerException, due to fragment is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (intent == null) {
      String err = "Fragment.startActivity(Intent intent, Bundle options) throw IllegalArgumentException, due to intent is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    try {
      fragment.startActivity(intent, options);
    } catch (IllegalStateException e) {
      String err = "Fragment.startActivity(Intent intent, Bundle options) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode) {
    if (fragment == null) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode) throw NullPointerException, due to fragment is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (intent == null) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode) throw IllegalArgumentException, due to intent is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    try {
      fragment.startActivityForResult(intent, requestCode);
    } catch (IllegalStateException e) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, Bundle options) {
    if (fragment == null) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode, Bundle options) throw NullPointerException, due to fragment is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    if (intent == null) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode, Bundle options) throw IllegalArgumentException, due to intent is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, new IllegalArgumentException(err));
      return;
    }
    try {
      fragment.startActivityForResult(intent, requestCode, options);
    } catch (IllegalStateException e) {
      String err = "Fragment.startActivityForResult(Intent intent, int requestCode, Bundle options) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static View getView(Fragment fragment) {
    if (fragment != null) {
      return fragment.getView();
    }
    String err = "Fragment.getView() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
    return null;
  }

}
