package com.panda912.defensor.internal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/8 09:31
 */
public class FragmentManagerDefensor {

  public static void popBackStack(FragmentManager manager) {
    if (manager == null) {
      String err = "FragmentManager.popBackStack() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      manager.popBackStack();
    } catch (IllegalStateException e) {
      String err = "FragmentManager.popBackStack() throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static void putFragment(FragmentManager manager, Bundle bundle, String key, Fragment fragment) {
    if (manager == null || bundle == null || key == null || fragment == null) {
      String err = "FragmentManager.putFragment(Bundle bundle, String key, Fragment fragment) throw NullPointerException, due to "
          + (manager == null ? "manager" : bundle == null ? "bundle" : key == null ? "key" : "fragment") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      manager.putFragment(bundle, key, fragment);
    } catch (IllegalStateException e) {
      String err = "FragmentManager.putFragment(Bundle bundle, String key, Fragment fragment) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static Fragment getFragment(FragmentManager manager, Bundle bundle, String key) {
    if (manager == null || bundle == null || key == null) {
      String err = "FragmentManager.getFragment(Bundle bundle, String key) throw NullPointerException, due to "
          + (manager == null ? "manager" : bundle == null ? "bundle" : "key") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return null;
    }
    try {
      manager.getFragment(bundle, key);
    } catch (IllegalStateException e) {
      String err = "FragmentManager.getFragment(Bundle bundle, String key) throw IllegalStateException";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
    return null;
  }

}
