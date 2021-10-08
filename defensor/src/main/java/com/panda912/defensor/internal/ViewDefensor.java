package com.panda912.defensor.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.recyclerview.widget.RecyclerView;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/8/23 13:19
 */
public class ViewDefensor {

  public static View inflate(Context context, int resource, ViewGroup root) {
    if (context != null) {
      return View.inflate(context, resource, root);
    }
    String error = "View.inflate(Context context, int resource, ViewGroup root) throw npe, due to context is null";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return null;
  }

  public static void setVisibility(View view, int visible) {
    if (view == null) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "View.setVisibility(int visible) throw npe, due to view is null", new NullPointerException("View.setVisibility(int visible) throw npe, due to view is null"));
    } else {
      view.setVisibility(visible);
    }
  }

  public static void loadUrl(WebView webView, String url) {
    if (webView == null || TextUtils.isEmpty(url)) {
      String error = "WebView.loadUrl(String url) throw npe, due to " + (webView == null ? "WebView is null." : "url is null.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      webView.loadUrl(url);
    } catch (Exception e) {
      String error = "WebView.loadUrl(String url) throw Exception";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    }
  }

  public static void loadData(WebView webView, String data, String mimeType, String encoding) {
    if (webView == null) {
      String error = "WebView.loadData(String data, String mimeType, String encoding) throw npe, due to WebView is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      webView.loadData(data, mimeType, encoding);
    } catch (Exception e) {
      String error = "WebView.loadData(String data, String mimeType, String encoding) throw Exception";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    }
  }

}
