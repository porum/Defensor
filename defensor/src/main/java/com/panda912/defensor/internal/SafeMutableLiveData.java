package com.panda912.defensor.internal;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * Created by panda on 2021/9/9 09:06
 */
public class SafeMutableLiveData<T> extends MutableLiveData<T> {

  @Override
  public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
    LiveDataDefensor.observe(this, owner, observer);
  }

  @Override
  public void observeForever(@NonNull Observer<? super T> observer) {
    LiveDataDefensor.observeForever(this, observer);
  }

}
