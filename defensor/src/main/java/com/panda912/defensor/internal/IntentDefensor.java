package com.panda912.defensor.internal;

import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Parcelable;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panda on 2021/8/17 13:35
 */
public class IntentDefensor {

  public static boolean getBooleanExtra(Intent intent, String name, boolean defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getBooleanExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getBooleanExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static byte getByteExtra(Intent intent, String name, byte defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getByteExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getByteExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static short getShortExtra(Intent intent, String name, short defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getShortExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getShortExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static char getCharExtra(Intent intent, String name, char defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getCharExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getCharExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static int getIntExtra(Intent intent, String name, int defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getIntExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getIntExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static long getLongExtra(Intent intent, String name, long defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getLongExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getLongExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static float getFloatExtra(Intent intent, String name, float defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getFloatExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getFloatExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static double getDoubleExtra(Intent intent, String name, double defValue) {
    try {
      if (intent == null) {
        return defValue;
      }
      return intent.getDoubleExtra(name, defValue);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getDoubleExtra throw BadParcelableException", e);
      return defValue;
    }
  }

  public static String getStringExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getStringExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getStringExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static CharSequence getCharSequenceExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getCharSequenceExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getCharSequenceExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static <T extends Parcelable> T getParcelableExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return (T) intent.<T>getParcelableExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getParcelableExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static Parcelable[] getParcelableArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getParcelableArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getParcelableArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getParcelableArrayListExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getParcelableArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static Serializable getSerializableExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getSerializableExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getSerializableExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static ArrayList<Integer> getIntegerArrayListExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getIntegerArrayListExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getIntegerArrayListExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static ArrayList<String> getStringArrayListExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getStringArrayListExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getStringArrayListExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static ArrayList<CharSequence> getCharSequenceArrayListExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getCharSequenceArrayListExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getCharSequenceArrayListExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static boolean[] getBooleanArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getBooleanArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getBooleanArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static byte[] getByteArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getByteArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getByteArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static short[] getShortArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getShortArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getShortArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static char[] getCharArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getCharArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getCharArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static int[] getIntArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getIntArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getIntArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static long[] getLongArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getLongArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getLongArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static float[] getFloatArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getFloatArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getFloatArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static double[] getDoubleArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getDoubleArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getDoubleArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static String[] getStringArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getStringArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getStringArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static CharSequence[] getCharSequenceArrayExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getCharSequenceArrayExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getCharSequenceArrayExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static Bundle getBundleExtra(Intent intent, String name) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getBundleExtra(name);
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getBundleExtra throw BadParcelableException", e);
      return null;
    }
  }

  public static Bundle getExtras(Intent intent) {
    try {
      if (intent == null) {
        return null;
      }
      return intent.getExtras();
    } catch (BadParcelableException e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.getExtras throw BadParcelableException", e);
      return null;
    }
  }

  public static Intent putExtra(Intent intent, String name, Serializable value) {
    if (intent == null) {
      return null;
    }
    if (!(value instanceof Map)) {
      return intent.putExtra(name, value);
    }
    try {
      return intent.putExtra(name, new HashMap((Map) value));
    } catch (Exception e) {
      CrashDefensor.onCrash(ErrorCode.BadParcelableException, "intent.putExtra throw BadParcelableException", e);
      return intent;
    }
  }
}
