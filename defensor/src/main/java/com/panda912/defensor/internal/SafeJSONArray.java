package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.Collection;

/**
 * Created by panda on 2021/9/1 15:37
 */
public class SafeJSONArray extends JSONArray {

  public SafeJSONArray() {
    super();
  }

  public SafeJSONArray(Collection copyFrom) {
    super(copyFrom);
  }

  public SafeJSONArray(JSONTokener readFrom) throws JSONException {
    super(readFrom);
  }

  public SafeJSONArray(String json) throws JSONException {
    super(json == null ? "[]" : json);
    if (json == null) {
      String error = "new JSONArray(String json) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NPEInNewJSONObject, error, new NullPointerException(error));
    }
  }

  public SafeJSONArray(Object array) throws JSONException {
    super(array);
  }

}
