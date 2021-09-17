package com.panda912.defensor.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.io.File;

/**
 * Created by panda on 2021/8/31 10:39
 */
public class ContextDefensor {

  public static String getPackageName(Context context) {
    if (context != null) {
      return context.getPackageName();
    }
    String error = "Context.getPackageName() throw npe, due to context is null.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return CrashDefensor.applicationId;
  }

  public static Object getSystemService(Context context, String name) {
    if (context == null) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "Context.getSystemService(String name) throw NullPointerException", new NullPointerException("Context.getSystemService(\"" + name + "\") throw NullPointerException"));
      return null;
    }
    try {
//      Object service = context.getSystemService(name);
//      if (service == null) {
//        String error = "Context.getSystemService(\"" + name + "\") return null.";
//        CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
//      }
//      return service;
      return context.getSystemService(name);
    } catch (Throwable e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.getSystemService(String name) throw DeadObjectException", e);
      return null;
    }
  }

  public static File getFilesDir(Context context) {
    File filesDir = context != null ? context.getFilesDir() : null;
    if (filesDir != null) {
      return filesDir;
    }
    String msg = "Context.getFilesDir() throw npe, due to " + (context == null ? "context is null" : "Context.getFilesDir() return null");
    CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
    String packageName = context != null ? context.getPackageName() : CrashDefensor.applicationId;
    return new File("/data/data/" + packageName + "/files");
  }

  public static boolean bindService(Context context, Intent service, ServiceConnection conn, int flags) {
    if (context == null || service == null) {
      String msg = "Context.bindService() throw NullPointerException due to " + (context == null ? "context" : "service") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return false;
    }
    try {
      return context.bindService(service, conn, flags);
    } catch (SecurityException e) {
      CrashDefensor.onCrash(ErrorCode.SecurityException, "Context.bindService() throw SecurityException", e);
      return false;
    }
  }

  public static int checkPermission(Context context, String permission, int pid, int uid) {
    if (context == null || permission == null) {
      String error = "Context.checkPermission(String permission, int pid, int uid) due to " + (context == null ? "context" : "permission") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    try {
      return context.checkPermission(permission, pid, uid);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.checkPermission(String permission, int pid, int uid) due to DeadSystemException", e);
      return PackageManager.PERMISSION_DENIED;
    }
  }

  public static int checkCallingPermission(Context context, String permission) {
    if (context == null || permission == null) {
      String error = "Context.checkCallingPermission(String permission) due to " + (context == null ? "context" : "permission") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    try {
      return context.checkCallingPermission(permission);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.checkCallingPermission(String permission) due to DeadSystemException", e);
      return PackageManager.PERMISSION_DENIED;
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static int checkSelfPermission(Context context, String permission) {
    if (context == null || permission == null) {
      String error = "Context.checkSelfPermission(String permission) due to " + (context == null ? "context" : "permission") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    try {
      return context.checkSelfPermission(permission);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.checkSelfPermission(String permission) due to DeadSystemException", e);
      return PackageManager.PERMISSION_DENIED;
    }
  }

  public static int checkCallingOrSelfPermission(Context context, String permission) {
    if (context == null || permission == null) {
      String error = "Context.checkCallingOrSelfPermission(String permission) due to " + (context == null ? "context" : "permission") + " is null";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return PackageManager.PERMISSION_DENIED;
    }
    try {
      return context.checkCallingOrSelfPermission(permission);
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.DeadObjectException, "Context.checkCallingOrSelfPermission(String permission) due to DeadSystemException", e);
      return PackageManager.PERMISSION_DENIED;
    }
  }

}
