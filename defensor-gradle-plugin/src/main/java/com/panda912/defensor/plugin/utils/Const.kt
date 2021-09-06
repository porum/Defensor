package com.panda912.defensor.plugin.utils

import org.objectweb.asm.Type
import java.io.File

/**
 * Created by panda on 2021/8/13 17:25
 */

const val DOT_CLASS = ".class"

const val BOOLEAN_CLASS = "java.lang.Boolean"
const val BYTE_CLASS = "java.lang.Byte"
const val SHORT_CLASS = "java.lang.Short"
const val INTEGER_CLASS = "java.lang.Integer"
const val LONG_CLASS = "java.lang.Long"
const val FLOAT_CLASS = "java.lang.Float"
const val DOUBLE_CLASS = "java.lang.Double"
const val STRING_CLASS = "java.lang.String"
const val CHAR_SEQUENCE_CLASS = "java.lang.CharSequence"
const val LIST_CLASS = "java.util.List"
const val ARRAY_LIST_CLASS = "java.util.ArrayList"
const val LINKED_LIST_CLASS = "java.util.LinkedList"
const val COPY_ON_WRITE_ARRAY_LIST_CLASS = "java.util.concurrent.CopyOnWriteArrayList"
const val MAP_CLASS = "java.util.Map"
const val HASHMAP_CLASS = "java.util.HashMap"
const val ARRAYMAP_CLASS = "java.util.ArrayMap"
const val CONCURRENT_HASHMAP_CLASS = "java.util.concurrent.ConcurrentHashMap"
const val JSONOBJECT_CLASS = "org.json.JSONObject"
const val JSONARRAY_CLASS = "org.json.JSONArray"
const val CONTEXT_CLASS = "android.content.Context"
const val CONTEXT_COMPAT_CLASS = "androidx.core.content.ContextCompat"
const val CONTEXT_WRAPPER_CLASS = "android.content.ContextWrapper"
const val APPLICATION_CLASS = "android.app.Application"
const val INTENT_CLASS = "android.content.Intent"
const val DIALOG_CLASS = "android.app.Dialog"
const val URI_CLASS = "android.net.Uri"
const val ACTIVITY_CLASS = "android.app.Activity"
const val WEBVIEW_CLASS = "android.webkit.WebView"
const val RECYCLER_CLASS = "androidx.recyclerview.widget.RecyclerView\$Recycler"
const val PACKAGE_MANAGER_CLASS = "android.content.pm.PackageManager"
const val PACKAGE_INFO_CLASS = "android.content.pm.PackageInfo"
const val WINDOW_MANAGER_CLASS = "android.view.WindowManager"
const val DISPLAY_CLASS = "android.view.Display"
const val DISPLAY_METRICS_CLASS = "android.util.DisplayMetrics"
const val CONNECTIVITY_MANAGER_CLASS = "android.net.ConnectivityManager"
const val ACTIVITY_MANAGER_CLASS = "android.app.ActivityManager"
const val TELEPHONY_MANAGER_CLASS = "android.telephony.TelephonyManager"


const val ACTIVITY_DEFENSOR = "com.panda912.defensor.internal.ActivityDefensor"
const val COLLECTION_DEFENSOR = "com.panda912.defensor.internal.CollectionDefensor"
const val CONTEXT_DEFENSOR = "com.panda912.defensor.internal.ContextDefensor"
const val DEAD_OBJECT_CRASH_HANDLER = "com.panda912.defensor.internal.DeadObjectCrashHandler"
const val FILE_DEFENSOR = "com.panda912.defensor.internal.FileDefensor"
const val INTENT_DEFENSOR = "com.panda912.defensor.internal.IntentDefensor"
const val JSON_DEFENSOR = "com.panda912.defensor.internal.JsonDefensor"
const val NULL_POINTER_EXCEPTION_DEFENSOR =
  "com.panda912.defensor.internal.NullPointerExceptionDefensor"
const val PRIMITIVE_TYPE_PARSER = "com.panda912.defensor.internal.PrimitiveTypeParser"
const val SAFE_DIALOG = "com.panda912.defensor.internal.SafeDialog"
const val SAFE_FILE = "com.panda912.defensor.internal.SafeFile"
const val SAFE_JSON_ARRAY = "com.panda912.defensor.internal.SafeJSONArray"
const val SAFE_JSON_OBJECT = "com.panda912.defensor.internal.SafeJSONObject"
const val STRING_DEFENSOR = "com.panda912.defensor.internal.StringDefensor"
const val TEXTVIEW_DEFENSOR = "com.panda912.defensor.internal.TextViewDefensor"
const val THROWABLE_DEFENSOR = "com.panda912.defensor.internal.ThrowableDefensor"
const val UNBOXING_DEFENSOR = "com.panda912.defensor.internal.UnboxingDefensor"
const val URI_DEFENSOR = "com.panda912.defensor.internal.UriDefensor"
const val VIEW_DEFENSOR = "com.panda912.defensor.internal.ViewDefensor"

val DEFENSOR_CLASS_LIST = listOf(
  ACTIVITY_DEFENSOR,
  COLLECTION_DEFENSOR,
  CONTEXT_DEFENSOR,
  DEAD_OBJECT_CRASH_HANDLER,
  FILE_DEFENSOR,
  INTENT_DEFENSOR,
  JSON_DEFENSOR,
  NULL_POINTER_EXCEPTION_DEFENSOR,
  PRIMITIVE_TYPE_PARSER,
  SAFE_DIALOG,
  SAFE_FILE,
  SAFE_JSON_ARRAY,
  SAFE_JSON_OBJECT,
  STRING_DEFENSOR,
  TEXTVIEW_DEFENSOR,
  THROWABLE_DEFENSOR,
  UNBOXING_DEFENSOR,
  URI_DEFENSOR,
  VIEW_DEFENSOR
).map {
  it.toInternalName().toClass()
}

fun String.toInternalName() = replace('.', '/')
fun String.toClassName() = replace('/', '.')
fun String.toClass() = this + DOT_CLASS
fun File.isDefensorClass() = DEFENSOR_CLASS_LIST.indexOfFirst { this.endsWith(it) } != -1


const val DESCRIPTOR_PUT_EXTRA =
  "(Ljava/lang/String;Ljava/io/Serializable;)Landroid/content/Intent;"

fun String.intentDescriptor() =
  StringBuilder(this).insert(1, "Landroid/content/Intent;").toString()

fun String.listDescriptor() =
  StringBuilder(this).insert(1, Type.getDescriptor(java.util.List::class.java)).toString()

fun String.mapDescriptor() =
  StringBuilder(this).insert(1, Type.getDescriptor(java.util.Map::class.java)).toString()

fun String.stringDescriptor() =
  StringBuilder(this).insert(1, Type.getDescriptor(java.lang.String::class.java)).toString()

fun String.charSequenceDescriptor() =
  StringBuilder(this).insert(1, Type.getDescriptor(java.lang.CharSequence::class.java)).toString()

fun String.convertToStaticDescriptor(descriptor: String) =
  StringBuilder(this).insert(1, descriptor).toString()