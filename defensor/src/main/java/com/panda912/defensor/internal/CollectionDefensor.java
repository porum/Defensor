package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by panda on 2021/8/17 13:39
 */
public class CollectionDefensor {

  ////////////////////////////////////////// array //////////////////////////////////////////

  public static boolean get(boolean[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "boolean[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return false;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "boolean[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("boolean[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return false;
  }

  public static byte get(byte[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "byte[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "byte[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("byte[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static char get(char[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "char[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "char[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("char[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static short get(short[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "short[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "short[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("short[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static int get(int[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "int[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "int[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("int[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static long get(long[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "long[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "long[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("long[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static double get(double[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "double[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "double[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("double[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static float get(float[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "float[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return 0;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "float[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("float[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return 0;
  }

  public static <T> T get(T[] arr, int i) {
    if (arr != null && i >= 0 && i < arr.length) {
      return arr[i];
    }
    if (arr == null) {
      String error = "Object[i] throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "Object[i] throw IndexOutOfBoundsException", new IndexOutOfBoundsException("Object[" + i + "] length " + arr.length + " throw IndexOutOfBoundsException"));
    return null;
  }

  ////////////////////////////////////////// list //////////////////////////////////////////

  public static <E> E get(List<E> list, int index) {
    if (list != null && index >= 0 && index < list.size()) {
      return list.get(index);
    }
    if (list == null) {
      String error = "List.get(i) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "List.get(i) throw IndexOutOfBoundsException", new IndexOutOfBoundsException("List.get(" + index + ") size " + list.size() + " throw IndexOutOfBoundsException"));
    return null;
  }

  public static <E> boolean add(List<E> list, E e) {
    try {
      return list.add(e);
    } catch (UnsupportedOperationException ex) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "List.add(e) throw UnsupportedOperationException due to " + ex.getMessage(), ex);
    } catch (ClassCastException ex) {
      CrashDefensor.onCrash(ErrorCode.ClassCastException, "List.add(e) throw ClassCastException due to " + ex.getMessage(), ex);
    } catch (NullPointerException ex) {
      String error = "List.add(e) throw NullPointerException, due to " +
          (list == null ? "this list is null." : "the specified element is null and this list does not permit null elements.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, ex);
    } catch (IllegalArgumentException ex) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "List.add(e) throw IllegalArgumentException due to " + ex.getMessage(), ex);
    }
    return false;
  }

  public static <E> void add(List<E> list, int index, E e) {
    try {
      list.add(index, e);
    } catch (UnsupportedOperationException ex) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "List.add(index, element) throw UnsupportedOperationException", ex);
    } catch (ClassCastException ex) {
      CrashDefensor.onCrash(ErrorCode.ClassCastException, "List.add(index, element) throw ClassCastException", ex);
    } catch (NullPointerException ex) {
      String error = "List.add(index, element) throw NullPointerException, due to " +
          (list == null ? "this list is null." : "the specified element is null and this list does not permit null elements.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error + ex.getMessage(), ex);
    } catch (IllegalArgumentException ex) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "List.add(index, element) throw IllegalArgumentException", ex);
    } catch (IndexOutOfBoundsException ex) {
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, "List.add(index, element) throw IndexOutOfBoundsException", ex);
    }
  }

  public static <E> E remove(List<E> list, int index) {
    if (list == null) {
      String error = "List.remove(index) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return null;
    }
    if (index < 0 || index >= list.size()) {
      String error = "List.remove(index) throw IndexOutOfBoundsException";
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, new IndexOutOfBoundsException("List.remove(" + index + ") size " + list.size() + " throw IndexOutOfBoundsException"));
      return null;
    }
    try {
      return list.remove(index);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "List.remove(index) throw UnsupportedOperationException", e);
    }
    return null;
  }

  public static boolean remove(List list, Object o) {
    try {
      return list.remove(o);
    } catch (ClassCastException e) {
      CrashDefensor.onCrash(ErrorCode.ClassCastException, "List.remove(o) throw ClassCastException", e);
    } catch (NullPointerException e) {
      String error = "List.remove(o) throw NullPointerException, due to " +
          (list == null ? "this list is null." : "the specified element is null and this list does not permit null elements.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "List.remove(o) throw UnsupportedOperationException", e);
    }
    return false;
  }

  public static <E> boolean addAll(List<E> list, Collection<? extends E> c) {
    try {
      return list.addAll(c);
    } catch (UnsupportedOperationException e) {
      String error = "List.addAll(c) throw UnsupportedOperationException due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, error, e);
    } catch (ClassCastException e) {
      String error = "List.addAll(c) throw ClassCastException due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    } catch (NullPointerException e) {
      String error = "List.addAll(c) throw NullPointerException, due to " +
          (list == null ? "this list is null." : c == null ? "the specified collection is null." : "the specified collection contains one or more null elements and this list does not permit null elements.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (IllegalArgumentException e) {
      String error = "List.addAll(c) throw IllegalArgumentException due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, e);
    }
    return false;
  }

  public static <E> boolean addAll(List<E> list, int index, Collection<? extends E> c) {
    try {
      return list.addAll(index, c);
    } catch (UnsupportedOperationException e) {
      String error = "List.addAll(index,c) throw UnsupportedOperationException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, error, e);
    } catch (ClassCastException e) {
      String error = "List.addAll(index,c) throw ClassCastException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    } catch (NullPointerException e) {
      String error = "List.addAll(index,c) throw NullPointerException, due to " +
          (list == null ? "this list is null." : c == null ? "the specified collection is null." : "the specified collection contains one or more null elements and this list does not permit null elements.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (IllegalArgumentException e) {
      String error = "List.addAll(index,c) throw IllegalArgumentException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, error, e);
    } catch (IndexOutOfBoundsException e) {
      String error = "List.addAll(index,c) throw IndexOutOfBoundsException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.IndexOutOfBoundsException, error, e);
    }
    return false;
  }

  public static <E> int size(List<E> list) {
    if (list != null) {
      return list.size();
    }
    String error = "List.size() throw NullPointerException";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static <E> void clear(List<E> list) {
    if (list == null) {
      String error = "List.clear() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      list.clear();
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.NullPointerException, "List.clear() throw UnsupportedOperationException", e);
    }
  }

  ////////////////////////////////////////// map //////////////////////////////////////////

  public static <K, V> V get(Map<K, V> map, K key) {
    try {
      return map.get(key);
    } catch (ClassCastException e) {
      String error = "Map.get() throw ClassCastException";
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    } catch (NullPointerException e) {
      String error = "Map.get() throw NullPointerException, due to " + (map == null ? "this map is null." : "the specified key is null and this map does not permit null keys.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (StackOverflowError e) {
      String error = "Map.get() throw StackOverflowError";
      CrashDefensor.onCrash(ErrorCode.StackOverflowError, error, e);
    }
    return null;
  }

  public static <K, V> V put(Map<K, V> map, K key, V value) {
    try {
      return map.put(key, value);
    } catch (UnsupportedOperationException e) {
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, "Map.put(key,value) throw UnsupportedOperationException due to" + e.getMessage(), e);
    } catch (ClassCastException e) {
      CrashDefensor.onCrash(ErrorCode.ClassCastException, "Map.put(key,value) throw ClassCastException due to" + e.getMessage(), e);
    } catch (NullPointerException e) {
      String error = "Map.put(key,value) throw NullPointerException, due to " + (map == null ? "this map is null." : "the specified key or value is null and this map does not permit null keys or values.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (IllegalArgumentException e) {
      CrashDefensor.onCrash(ErrorCode.IllegalArgumentException, "Map.put(key,value) throw IllegalArgumentException due to" + e.getMessage(), e);
    }
    return null;
  }

  public static <K, V> int size(Map<K, V> map) {
    if (map != null) {
      return map.size();
    }
    String error = "Map.size() throw NullPointerException.";
    CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
    return 0;
  }

  public static void clear(Map map) {
    if (map == null) {
      String error = "Map.clear() throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, new NullPointerException(error));
      return;
    }
    try {
      map.clear();
    } catch (UnsupportedOperationException e) {
      String error = "Map.clear() throw UnsupportedOperationException due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.UnsupportedOperationException, error, e);
    }
  }

  public static boolean containsKey(Map map, Object key) {
    try {
      return map.containsKey(key);
    } catch (NullPointerException e) {
      String error = "Map.containsKey(key) throw NullPointerException, due to " + (map == null ? "this map is null." : "the specified key is null and this map does not permit null keys.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (ClassCastException e) {
      String error = "Map.containsKey(key) throw ClassCastException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    }
    return false;
  }

  public static boolean containsValue(Map map, Object value) {
    try {
      return map.containsValue(value);
    } catch (NullPointerException e) {
      String error = "Map.containsValue(value) throw NullPointerException, due to " + (map == null ? "this map is null." : "the specified value is null and this map does not permit null values.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    } catch (ClassCastException e) {
      String error = "Map.containsValue(value) throw ClassCastException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    }
    return false;
  }

  public static <K, V> void putAll(Map<K, V> map, Map<? extends K, ? extends V> m) {
    try {
      map.putAll(m);
    } catch (ClassCastException e) {
      String error = "Map.putAll(m) throw ClassCastException, due to " + e.getMessage();
      CrashDefensor.onCrash(ErrorCode.ClassCastException, error, e);
    } catch (NullPointerException e) {
      String error = "Map.putAll(m) throw NullPointerException, due to " + (map == null ? "this map is null." : m == null ? "the specified map is null." : "this map does not permit null keys or values, and the specified map contains null keys or values.");
      CrashDefensor.onCrash(ErrorCode.NullPointerException, error, e);
    }
  }

}
