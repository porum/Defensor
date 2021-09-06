package com.panda912.defensor.internal;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.util.Collections;
import java.util.List;

/**
 * Created by panda on 2021/9/6 08:55
 */
public class DeadObjectCrashHandler {

  ///////////////////////////////// PackageManager /////////////////////////////////

  public static PackageInfo getPackageInfo(PackageManager packageManager, String packageName, int flags) throws PackageManager.NameNotFoundException {
    if (packageManager == null || TextUtils.isEmpty(packageName)) {
      String error = "PackageManager.getPackageInfo() due to " + (packageManager == null ? "packageManager" : "packageName") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      throw new NameNotFoundException(error);
    }
    try {
      return packageManager.getPackageInfo(packageName, flags);
    } catch (NameNotFoundException e) {
      CrashDefensor.onCrash(ErrorCode.PackageManagerNameNotFoundException, "PackageManager.getPackageInfo() due to Package manager has died", e);
      throw e;
    }
  }

  public static ApplicationInfo getApplicationInfo(PackageManager packageManager, String packageName, int flags) throws PackageManager.NameNotFoundException {
    if (packageManager == null || TextUtils.isEmpty(packageName)) {
      String error = "PackageManager.getApplicationInfo() due to " + (packageManager == null ? "packageManager" : "packageName") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      throw new NameNotFoundException(error);
    }
    try {
      return packageManager.getApplicationInfo(packageName, flags);
    } catch (NameNotFoundException e) {
      CrashDefensor.onCrash(ErrorCode.PackageManagerNameNotFoundException, "PackageManager.getApplicationInfo() due to Package manager has died", e);
      throw e;
    }
  }

  ///////////////////////////////// Display /////////////////////////////////

  public static Display getDefaultDisplay(WindowManager windowManager) {
    if (windowManager == null) {
      String error = "WindowManager.getDefaultDisplay() throw NullPointerException, due to windowManager is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return windowManager.getDefaultDisplay();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "WindowManager.getDefaultDisplay() due to DeadSystemException", e);
      return null;
    }
  }

  public static void getDisplaySize(Display display, Point point) {
    if (display == null || point == null) {
      String error = "Display.getSize() throw NullPointerException, due to " + (display == null ? "display" : "point") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      display.getSize(point);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Display.getSize() due to DeadSystemException", e);
    }
  }

  public static float getDisplayRefreshRate(Display display) {
    if (display == null) {
      String error = "Display.getDisplayRefreshRate() throw NullPointerException, due to display is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 60.0f;
    }
    try {
      return display.getRefreshRate();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Display.getDisplayRefreshRate() due to DeadSystemException", e);
      return 60.0f;
    }
  }

  public static void getDisplayMetrics(Display display, DisplayMetrics outMetrics) {
    if (display == null || outMetrics == null) {
      String error = "Display.getMetrics() throw NullPointerException, due to " + (display == null ? "display" : "outMetrics") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      display.getMetrics(outMetrics);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Display.getMetrics() due to DeadSystemException", e);
    }
  }

  ///////////////////////////////// Broadcast /////////////////////////////////

  public static void sendBroadcast(Context context, Intent intent) {
    if (context == null || intent == null) {
      String error = "context.sendBroadcast() throw NullPointerException, due to context or intent is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      context.sendBroadcast(intent);
    } catch (Exception e) {
      String error = "context.sendBroadcast() throw unexpected exception.";
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
    }
  }

//  public static void sendBroadcast(ContextWrapper contextWrapper, Intent intent) {
//    if (contextWrapper == null || intent == null) {
//      String error = "contextWrapper.sendBroadcast() throw NullPointerException, due to contextWrapper or intent is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return;
//    }
//    try {
//      contextWrapper.sendBroadcast(intent);
//    } catch (Exception e) {
//      String error = "contextWrapper.sendBroadcast() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//  }
//
//  public static void sendBroadcast(Application application, Intent intent) {
//    if (application == null || intent == null) {
//      String error = "application.sendBroadcast() throw NullPointerException, due to application or intent is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return;
//    }
//    try {
//      application.sendBroadcast(intent);
//    } catch (Exception e) {
//      String error = "application.sendBroadcast() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//  }
//
//  public static void sendBroadcast(Activity activity, Intent intent) {
//    if (activity == null || intent == null) {
//      String error = "activity.sendBroadcast() throw NullPointerException, due to activity or intent is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return;
//    }
//    try {
//      activity.sendBroadcast(intent);
//    } catch (Exception e) {
//      String error = "activity.sendBroadcast() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//  }

  public static Intent registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter) {
    if (context == null) {
      String error = "context.registerReceiver() throw NullPointerException, due to context is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return context.registerReceiver(receiver, filter);
    } catch (Exception e) {
      String error = "Context.registerReceiver() throw unexpected exception.";
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
    }
    return null;
  }

//  public static Intent registerReceiver(ContextWrapper context, BroadcastReceiver receiver, IntentFilter filter) {
//    if (context == null) {
//      String error = "contextWrapper.registerReceiver() throw NullPointerException, due to contextWrapper is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return null;
//    }
//    try {
//      return context.registerReceiver(receiver, filter);
//    } catch (Exception e) {
//      String error = "contextWrapper.registerReceiver() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//    return null;
//  }
//
//  public static Intent registerReceiver(Application application, BroadcastReceiver receiver, IntentFilter filter) {
//    if (application == null) {
//      String error = "application.registerReceiver() throw NullPointerException, due to application is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return null;
//    }
//    try {
//      return application.registerReceiver(receiver, filter);
//    } catch (Exception e) {
//      String error = "application.registerReceiver() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//    return null;
//  }
//
//  public static Intent registerReceiver(Activity activity, BroadcastReceiver receiver, IntentFilter filter) {
//    if (activity == null) {
//      String error = "activity.registerReceiver() throw NullPointerException, due to activity is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      return null;
//    }
//    try {
//      return activity.registerReceiver(receiver, filter);
//    } catch (Exception e) {
//      String error = "activity.registerReceiver() throw unexpected exception.";
//      CrashDefensor.onCrash(ErrorCode.DeadObjectException, error, e);
//    }
//    return null;
//  }

  public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
    if (context != null && receiver != null) {
      try {
        context.unregisterReceiver(receiver);
      } catch (Exception e) {
        CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.unregisterReceiver() throw unexpected exception.", e);
      }
    } else if (context == null) {
      String error = "context.unregisterReceiver() throw NullPointerException, due to context is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    } else {
      String error = "context.unregisterReceiver() throw IllegalArgumentException, due to receiver is null.";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
    }
  }

//  public static void unregisterReceiver(ContextWrapper context, BroadcastReceiver receiver) {
//    if (context != null && receiver != null) {
//      try {
//        context.unregisterReceiver(receiver);
//      } catch (Exception e) {
//        CrashDefensor.onCrash(ErrorCode.DeadObjectException, "contextWrapper.unregisterReceiver() throw unexpected exception.", e);
//      }
//    } else if (context == null) {
//      String error = "contextWrapper.unregisterReceiver() throw NullPointerException, due to contextWrapper is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//    } else {
//      String error = "contextWrapper.unregisterReceiver() throw IllegalArgumentException, due to receiver is null.";
//      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
//    }
//  }
//
//  public static void unregisterReceiver(Application application, BroadcastReceiver receiver) {
//    if (application != null && receiver != null) {
//      try {
//        application.unregisterReceiver(receiver);
//      } catch (Exception e) {
//        CrashDefensor.onCrash(ErrorCode.DeadObjectException, "application.unregisterReceiver() throw unexpected exception.", e);
//      }
//    } else if (application == null) {
//      String error = "application.unregisterReceiver() throw NullPointerException, due to application is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//    } else {
//      String error = "application.unregisterReceiver() throw IllegalArgumentException, due to receiver is null.";
//      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
//    }
//  }
//
//  public static void unregisterReceiver(Activity activity, BroadcastReceiver receiver) {
//    if (activity != null && receiver != null) {
//      try {
//        activity.unregisterReceiver(receiver);
//      } catch (Exception e) {
//        CrashDefensor.onCrash(ErrorCode.DeadObjectException, "activity.unregisterReceiver() throw unexpected exception.", e);
//      }
//    } else if (activity == null) {
//      String error = "activity.unregisterReceiver() throw NullPointerException, due to activity is null.";
//      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//    } else {
//      String error = "activity.unregisterReceiver() throw IllegalArgumentException, due to receiver is null.";
//      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
//    }
//  }

  ///////////////////////////////// Permission /////////////////////////////////

  public static int checkSelfPermissionWithContextCompat(Context context, String permission) {
    if (context == null) {
      String error = "ContextCompat.checkSelfPermission() due to context is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    if (permission == null) {
      String error = "ContextCompat.checkSelfPermission() due to permission is null";
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, new IllegalArgumentException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    try {
      return ContextCompat.checkSelfPermission(context, permission);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "ContextCompat.checkSelfPermission() due to DeadSystemException", e);
      return PackageManager.PERMISSION_DENIED;
    }
  }

  ///////////////////////////////// NetworkInfo /////////////////////////////////

  public static NetworkInfo getActiveNetworkInfo(ConnectivityManager connectivityManager) {
    if (connectivityManager == null) {
      String error = "ConnectivityManager.getActiveNetworkInfo() due to connectivityManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    try {
      return connectivityManager.getActiveNetworkInfo();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "ConnectivityManager.getActiveNetworkInfo() due to DeadSystemException", e);
      return null;
    }
  }

  public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses(ActivityManager activityManager) {
    if (activityManager == null) {
      String error = "ActivityManager.getRunningAppProcesses() due to activityManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return Collections.emptyList();
    }
    try {
      return activityManager.getRunningAppProcesses();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "ActivityManager.getRunningAppProcesses() due to DeadSystemException", e);
      return Collections.emptyList();
    }
  }

  ///////////////////////////////// TelephonyManager /////////////////////////////////

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static String getMeid(TelephonyManager telephonyManager) {
    if (telephonyManager == null) {
      String error = "TelephonyManager.getMeid() due to telephonyManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    try {
      return telephonyManager.getMeid();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "TelephonyManager.getMeid() due to TelephonyManager has died", e);
      return "";
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static String getMeid(TelephonyManager telephonyManager, int slotIndex) {
    if (telephonyManager == null) {
      String error = "TelephonyManager.getMeid(slotIndex) due to telephonyManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    try {
      return telephonyManager.getMeid(slotIndex);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "TelephonyManager.getMeid(slotIndex) due to TelephonyManager has died", e);
      return "";
    }
  }

  @SuppressLint("HardwareIds")
  public static String getDeviceId(TelephonyManager telephonyManager) {
    if (telephonyManager == null) {
      String error = "TelephonyManager.getDeviceId() due to telephonyManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    try {
      return telephonyManager.getDeviceId();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "TelephonyManager.getDeviceId() due to TelephonyManager has died", e);
      return "";
    }
  }

  @SuppressLint("HardwareIds")
  @RequiresApi(api = Build.VERSION_CODES.M)
  public static String getDeviceId(TelephonyManager telephonyManager, int slotIndex) {
    if (telephonyManager == null) {
      String error = "TelephonyManager.getDeviceId(slotIndex) due to telephonyManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    try {
      return telephonyManager.getDeviceId(slotIndex);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "TelephonyManager.getDeviceId(slotIndex) due to TelephonyManager has died", e);
      return "";
    }
  }

  @SuppressLint("HardwareIds")
  public static String getSubscriberId(TelephonyManager telephonyManager) {
    if (telephonyManager == null) {
      String error = "TelephonyManager.getSubscriberId() due to telephonyManager is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return "";
    }
    try {
      return telephonyManager.getSubscriberId();
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "TelephonyManager.getSubscriberId() due to TelephonyManager has died", e);
      return "";
    }
  }

}
