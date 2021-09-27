package com.panda912.defensor.sample;

import android.util.Log;

/**
 * Created by panda on 2021/9/27 10:33
 */
public class Test {

  private static final String TAG = "Test";

  public static void main(String[] args) {
    boolean[] booleans = new boolean[]{true, false, true, true};
    byte[] bytes = new byte[]{0, 1, 2, 3};
    short[] shorts = new short[]{0, 1, 2, 3};
    int[] ints = new int[]{0, 1, 2, 3};
    long[] longs = new long[]{0L, 1L, 2L, 3L};
    float[] floats = new float[]{0.0F, 1.0F, 2.0F, 3.0F};
    double[] doubles = new double[]{0.0D, 1.0D, 2.0D, 3.0D};
    char[] chars = new char[]{'0', '1', '2', '3'};
    String[] strings = new String[]{"p", "a", "n", "d", "a"};
    Log.d("Test", "boolean0: " + booleans[0]);
    Log.d("Test", "byte0: " + bytes[0]);
    Log.d("Test", "short0: " + shorts[0]);
    Log.d("Test", "int0: " + ints[0]);
    Log.d("Test", "long0: " + longs[0]);
    Log.d("Test", "float0: " + floats[0]);
    Log.d("Test", "double0: " + doubles[0]);
    Log.d("Test", "char0: " + chars[0]);
    Log.d("Test", "string0: " + strings[0]);
  }
}
