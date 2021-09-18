package com.panda912.defensor.samplelib;

import com.panda912.defensor.internal.CollectionDefensor;

/**
 * Created by panda on 2021/9/14 10:48
 */
public class ArrayDemo {
  private static final String TAG = "ArrayDemo";

  public static void test(String[] strArr) {
    Logger.i(TAG, strArr[2]);
    Logger.w(TAG, strArr[2]);
    Logger.i(TAG, CollectionDefensor.get(strArr, 2));
    Logger.w(TAG, CollectionDefensor.get(strArr, 2));
  }
}