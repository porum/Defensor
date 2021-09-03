package com.panda912.defensor.sample;

import com.panda912.defensor.internal.CollectionDefensor;

import java.util.Locale;

/**
 * Created by panda on 2021/8/23 9:19
 */
class Main {
  public static void main(String[] args) {
    String[] arr = new String[3];
    String ass = (String) CollectionDefensor.get(arr, 3);

    CharSequence charSequence = "dadada";
    charSequence.subSequence(1, 2);

    String.format("%d", 2);
    String.format(Locale.getDefault(), "%d", 2);


    Integer.parseInt("2");
    Integer.parseInt("2", 16);
    Short.parseShort("3");
    Short.parseShort("3", 2);
    Long.parseLong("12");
    Long.parseLong("12", 10);
    Double.parseDouble("2.666");
    Boolean.parseBoolean("true");
    Float.parseFloat("123.6");
    Byte.parseByte("6");
    Byte.parseByte("6", 8);
  }
}
