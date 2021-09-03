package com.panda912.defensor.internal;

import com.panda912.defensor.CrashDefensor;
import com.panda912.defensor.ErrorCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by panda on 2021/8/17 13:39
 */
public class JsonDefensor {

  public static JSONObject createJSONObjectSafely(String json) throws JSONException {
    return new JSONObject(getJsonObjectString(json));
  }

  public static String getJsonObjectString(String json) {
    if (json != null) {
      return json;
    }
    CrashDefensor.onCrash(ErrorCode.NPEInNewJSONObject, "new JSONObject(String) throw NullPointerException", new NullPointerException("new JSONObject(String) throw NullPointerException"));
    return "{}";
  }

  public static JSONArray createJSONArraySafely(String json) throws JSONException {
    return new JSONArray(getJsonArrayString(json));
  }

  private static String getJsonArrayString(String json) {
    if (json != null) {
      return json;
    }
    CrashDefensor.onCrash(ErrorCode.NPEInNewJSONObject, "new JsonArray(String) throw NullPointerException", new NullPointerException("new JsonArray(String) throw NullPointerException"));
    return "[]";
  }

}
