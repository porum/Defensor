package com.panda912.defensor.internal;

import android.net.Uri;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by panda on 2021/8/31 11:18
 */
public class UriDefensor {

  public static Set<String> getQueryParameterNames(Uri uri) {
    if (uri == null) {
      String error = "Uri.getQueryParameterNames() throw NullPointerException.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return Collections.emptySet();
    }
    try {
      return uri.getQueryParameterNames();
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "Uri.getQueryParameterNames() throw UnsupportedOperationException.", e);
      return Collections.emptySet();
    }
  }

  public static List<String> getQueryParameters(Uri uri, String key) {
    if (uri == null || key == null) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "Uri.getQueryParameters(String key) throw NullPointerException.", new NullPointerException("Uri.getQueryParameters(" + key + ") throw NullPointerException."));
      return Collections.emptyList();
    }
    try {
      return uri.getQueryParameters(key);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "Uri.getQueryParameters(String key) throw UnsupportedOperationException.", e);
      return Collections.emptyList();
    }
  }

  public static String getQueryParameter(Uri uri, String key) {
    if (uri == null || key == null) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "Uri.getQueryParameter(String key) throw NullPointerException.", new NullPointerException("Uri.getQueryParameter(" + key + ") throw NullPointerException."));
      return "";
    }
    try {
      return uri.getQueryParameter(key);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "Uri.getQueryParameter(String key) throw UnsupportedOperationException.", e);
      return "";
    }
  }

  public static boolean getBooleanQueryParameter(Uri uri, String key, boolean defaultValue) {
    if (uri == null || key == null) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "Uri.getBooleanQueryParameter(String key, boolean defaultValue) throw NullPointerException.", new NullPointerException("Uri.getBooleanQueryParameter(\"" + key + "\", " + defaultValue + ") throw NullPointerException."));
      return defaultValue;
    }
    try {
      return uri.getBooleanQueryParameter(key, defaultValue);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "Uri.getBooleanQueryParameter(String key, boolean defaultValue) throw UnsupportedOperationException.", e);
      return defaultValue;
    }
  }
}
