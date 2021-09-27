package com.panda912.defensor.internal;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

/**
 * Created by panda on 2021/9/8 10:00
 */
public class LiveDataDefensor {

  public static <T> void observe(LiveData<T> liveData, LifecycleOwner owner, Observer<? super T> observer) {
    if (liveData == null || owner == null || observer == null) {
      String err = "LiveData.observe(owner, observer) throw NullPointerException, due to "
          + (liveData == null ? "liveData" : owner == null ? "owner" : "observer") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      liveData.observe(owner, observer);
    } catch (IllegalArgumentException e) {
      String err = "LiveData.observe(owner, observer) throw IllegalArgumentException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, e);
    } catch (IllegalStateException e) {
      String err = "LiveData.observe(owner, observer) throw IllegalStateException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static <T> void observeForever(LiveData<T> liveData, Observer<? super T> observer) {
    if (liveData == null || observer == null) {
      String err = "LiveData.observeForever(observer) throw NullPointerException, due to "
          + (liveData == null ? "liveData" : "observer") + " is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    try {
      liveData.observeForever(observer);
    } catch (IllegalArgumentException e) {
      String err = "LiveData.observeForever(observer) throw IllegalArgumentException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, err, e);
    } catch (IllegalStateException e) {
      String err = "LiveData.observeForever(observer) throw IllegalStateException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalStateException, err, e);
    }
  }

  public static <T> void postValue(MutableLiveData<T> liveData, T value) {
    if (liveData == null) {
      String err = "LiveData.postValue(value) throw NullPointerException, due to LiveData is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    liveData.postValue(value);
  }

  public static <T> void setValue(MutableLiveData<T> liveData, T value) {
    if (liveData == null) {
      String err = "LiveData.setValue(value) throw NullPointerException, due to LiveData is null.";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, err, new NullPointerException(err));
      return;
    }
    liveData.setValue(value);
  }

}
