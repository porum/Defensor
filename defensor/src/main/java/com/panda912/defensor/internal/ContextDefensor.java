package com.panda912.defensor.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

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
      Object service = context.getSystemService(name);
      if (service == null) {
        String error = "Context.getSystemService(\"" + name + "\") return null.";
        CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      }
      return service;
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

}
