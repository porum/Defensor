package com.panda912.defensor.samplelib

import org.json.JSONObject


fun getValue(key: String, jsonObj: String): String =
  JSONObject(jsonObj).takeIf { it.has(key) }?.getString(key) ?: ""

fun getIntValue(key: String, jsonObj: String): Int =
  JSONObject(jsonObj).takeIf { it.has(key) }?.getInt(key) ?: 0

fun getBooleanValue(key: String, jsonObj: String): Boolean =
  JSONObject(jsonObj).takeIf { it.has(key) }?.getBoolean(key) ?: false

fun containsKey(key: String, jsonObj: String): Boolean = JSONObject(jsonObj).has(key)

