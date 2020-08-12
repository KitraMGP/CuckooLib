package com.github.zi_jing.cuckoolib.util;

public class ArraysUtil {
  @SuppressWarnings("unchecked")
  public static <T> T[] concat(T[]... arrays) {
    int len = 0;
    for (T[] arr : arrays) len += arr.length;
    T[] result = (T[]) (new Object[len]);
    int index = 0;
    for (T[] arr : arrays) {
      System.arraycopy(arr, 0, result, index, arr.length);
      index += arr.length;
    }
    return result;
  }
}
