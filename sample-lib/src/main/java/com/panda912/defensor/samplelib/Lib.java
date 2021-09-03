package com.panda912.defensor.samplelib;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by panda on 2021/8/24 11:19
 */
public class Lib {
  private static final String TAG = "Lib";

  private static final List<String> list = new ArrayList<>();

  public static void doSth() {
    Character num = Character.valueOf('3');
    char num1 = num;

    Double dou = Double.valueOf(3.2);
    double dou1 = dou;

    try {
      list.add("1");
      list.add("2");
      list.add("3");
      Log.i(TAG, "list size: " + list.size());
    } catch (RuntimeException e) {
      Log.e(TAG, "error: " + e.getMessage());
    }

  }

  public void doSth2() {
    List<String> list = new ArrayList<>();
    list.add("1");
    list.add("2");
    list.add("3");
    LinkedList<String> subList = new LinkedList<>();
    subList.add("6");
    list.addAll(subList);
    list.addAll(3, subList);

    list.clear();
    Log.i(TAG, "list size: " + list.size());

    "http://panda912.com".lastIndexOf("a");
    "http://panda912.com".startsWith("http");
    "http://panda912.com".endsWith("com");

    Map<String, String> map = new HashMap<>();
    map.put("name", "jack");
    map.containsKey("name");
    map.containsValue("jack");
    map.clear();
    HashMap<String, String> subMap = new HashMap<>();
    subMap.put("age", "20");
    map.putAll(subMap);

  }
}
