package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Collections;
import java.util.Map;

/**
 * Created by panda on 2021/9/1 15:37
 */
public class SafeJSONObject extends JSONObject {

  public SafeJSONObject() {
    super();
  }

  public SafeJSONObject(Map copyFrom) {
    super(copyFrom == null ? Collections.emptyMap() : copyFrom);
    if (copyFrom == null) {
      String error = "new JSONObject(Map copyFrom) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NPEInNewJSONObject, error, new NullPointerException(error));
    }
  }

  public SafeJSONObject(JSONTokener readFrom) throws JSONException {
    super(readFrom);
  }

  public SafeJSONObject(String json) throws JSONException {
    super(json == null ? "{}" : json);
    if (json == null) {
      String error = "new JSONObject(String json) throw NullPointerException";
      CrashDefensor.onCrash(ErrorCode.NPEInNewJSONObject, error, new NullPointerException(error));
    }
  }

  public SafeJSONObject(JSONObject copyFrom, String[] names) throws JSONException {
    super(copyFrom, names);
  }

}
