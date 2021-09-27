package com.panda912.defensor.samplelib;

import com.panda912.defensor.internal.CollectionDefensor;

/**
 * Created by panda on 2021/9/14 10:48
 */
public class ArrayDemo {
  private static final String TAG = "ArrayDemo";

  private static int[] ints = {1, 2, 3, 4, 5, 6, 7};
  private static String[] strs = {"1", "2", "3", "4", "5", "6", "7", "8"};
  private String[] strings = {"1", "2", "3", "4", "5", "6", "7"};

  private static int indexx;

  public static void test(String[] strArr, int index) {
    int i = ints[6];
    String s = strs[index];
    String str = strArr[6];
  }

  public static void test1(String[] input) {
    Logger.i(TAG, input[2]);
    Logger.w(TAG, input[2]);
    Logger.i(TAG, CollectionDefensor.get(input, 2));
    Logger.w(TAG, CollectionDefensor.get(input, 2));
  }

  public static void test2(byte[] bytes) {
    byte b = bytes[10];
    System.out.println(bytes[9]);
  }

  public static void test3(boolean[] bools) {
    boolean bool = bools[10];
    System.out.println(bools[9]);
  }
}