package com.panda912.defensor.internal;

import android.animation.ValueAnimator;
import android.os.Looper;
import android.util.AndroidRuntimeException;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/22 09:12
 */
public class ValueAnimatorDefensor {

  public static void start(ValueAnimator animator) {
    if (animator == null) {
      String msg = "ValueAnimator.start() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    if (Looper.myLooper() == null) {
      String msg = "ValueAnimator.start() throw AndroidRuntimeException, Animators may only be run on Looper threads";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, msg, new AndroidRuntimeException(msg));
      return;
    }
    animator.start();
  }

  public static void end(ValueAnimator animator) {
    if (animator == null) {
      String msg = "ValueAnimator.end() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    if (Looper.myLooper() == null) {
      String msg = "ValueAnimator.end() throw AndroidRuntimeException, Animators may only be run on Looper threads";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, msg, new AndroidRuntimeException(msg));
      return;
    }
    animator.end();
  }

  public static void cancel(ValueAnimator animator) {
    if (animator == null) {
      String msg = "ValueAnimator.cancel() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    if (Looper.myLooper() == null) {
      String msg = "ValueAnimator.cancel() throw AndroidRuntimeException, Animators may only be run on Looper threads";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, msg, new AndroidRuntimeException(msg));
      return;
    }
    animator.cancel();
  }

  public static void resume(ValueAnimator animator) {
    if (animator == null) {
      String msg = "ValueAnimator.resume() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    if (Looper.myLooper() == null) {
      String msg = "ValueAnimator.resume() throw AndroidRuntimeException, Animators may only be resumed from the same thread that the animator was started on";
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, msg, new AndroidRuntimeException(msg));
      return;
    }
    animator.resume();
  }

  public static void reverse(ValueAnimator animator) {
    if (animator == null) {
      String msg = "ValueAnimator.reverse() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, msg, new NullPointerException(msg));
      return;
    }
    try {
      animator.reverse();
    } catch (AndroidRuntimeException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, e.getMessage(), e);
    }
  }

  public static ValueAnimator setDuration(ValueAnimator animator, long duration) {
    try {
      return animator.setDuration(duration);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, e.getMessage(), e);
    }
    return animator;
  }

}
