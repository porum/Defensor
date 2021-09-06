package com.panda912.defensor;

/**
 * Created by panda on 2021/8/17 13:43
 */
public class CrashDefensor {
  private static ICrashCaughtListener crashCaughtListener = null;

  public static String applicationId = "";
  public static boolean enableReport = false;
  public static boolean enableThrow = true;

  public static void init(Config config) {
    applicationId = config.applicationId;
    enableReport = config.enableReport;
    enableThrow = config.enableThrow;
  }

  public static void onCrash(int errorCode, String msg, Throwable th) {
    if (crashCaughtListener != null) {
      crashCaughtListener.onCrashCaught(errorCode, msg, th);
    }

    if (enableThrow && (th instanceof RuntimeException)) {
      throw ((RuntimeException) th);
    }
  }

  public static void setCrashCaughtListener(ICrashCaughtListener listener) {
    crashCaughtListener = listener;
  }

  public static class Config {
    private String applicationId;
    private boolean enableReport;
    private boolean enableThrow;

    public Config setApplicationId(String applicationId) {
      this.applicationId = applicationId;
      return this;
    }

    public Config setEnableReport(boolean enableReport) {
      this.enableReport = enableReport;
      return this;
    }

    public Config setEnableThrow(boolean enableThrow) {
      this.enableThrow = enableThrow;
      return this;
    }
  }

}
