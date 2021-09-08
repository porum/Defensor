package com.panda912.defensor.plugin.bytecode

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.view.View
import android.widget.TextView
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import java.io.File

/**
 * Created by panda on 2021/8/13 17:33
 */
class DefensorMethodVisitor(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM7, mv) {

  override fun visitTypeInsn(opcode: Int, type: String?) {
    if (opcode == Opcodes.NEW) {
      if (type == JSONOBJECT_CLASS.toInternalName()) {
        super.visitTypeInsn(opcode, SAFE_JSON_OBJECT.toInternalName())
        return
      }
      if (type == JSONARRAY_CLASS.toInternalName()) {
        super.visitTypeInsn(opcode, SAFE_JSON_ARRAY.toInternalName())
        return
      }
      if (type == Type.getInternalName(File::class.java)) {
        super.visitTypeInsn(opcode, SAFE_FILE.toInternalName())
        return
      }
    }
    super.visitTypeInsn(opcode, type)
  }

  /**
   * TODO handle AALOAD and BALOAD
   */
  override fun visitInsn(opcode: Int) {

    val descriptor = when (opcode) {
      Opcodes.IALOAD -> "([II)I"
      Opcodes.LALOAD -> "([JI)J"
      Opcodes.FALOAD -> "([FI)F"
      Opcodes.DALOAD -> "([DI)D"
      Opcodes.CALOAD -> "([CI)C"
      Opcodes.SALOAD -> "([SI)S"
      else -> null
    }

    if (descriptor.isNullOrEmpty()) {
      super.visitInsn(opcode)
    } else {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        "get",
        descriptor,
        false
      )
    }
  }

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    // Unboxing
    if (opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (owner == Type.getInternalName(java.lang.Short::class.java) && name == "shortValue" && descriptor == "()S") ||
        (owner == Type.getInternalName(java.lang.Integer::class.java) && name == "intValue" && descriptor == "()I") ||
        (owner == Type.getInternalName(java.lang.Long::class.java) && name == "longValue" && descriptor == "()J") ||
        (owner == Type.getInternalName(java.lang.Float::class.java) && name == "floatValue" && descriptor == "()F") ||
        (owner == Type.getInternalName(java.lang.Double::class.java) && name == "doubleValue" && descriptor == "()D") ||
        (owner == Type.getInternalName(java.lang.Byte::class.java) && name == "byteValue" && descriptor == "()B") ||
        (owner == Type.getInternalName(java.lang.Boolean::class.java) && name == "booleanValue" && descriptor == "()Z") ||
        (owner == Type.getInternalName(java.lang.Character::class.java) && name == "charValue" && descriptor == "()C")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          UNBOXING_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor(Type.getDescriptor(Class.forName(owner.toClassName()))),
          isInterface
        )
        return
      }
    }


    // CharSequence
    if (opcode == Opcodes.INVOKEINTERFACE &&
      owner == CHAR_SEQUENCE_CLASS.toInternalName() &&
      (name == "length" ||
          name == "charAt" ||
          name == "subSequence" ||
          name == "toString")
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        STRING_DEFENSOR.toInternalName(),
        name,
        descriptor.charSequenceDescriptor(),
        isInterface
      )
      return
    }

    // String
    if (opcode == Opcodes.INVOKEVIRTUAL &&
      owner == STRING_CLASS.toInternalName() &&
      ((name == "substring" && descriptor == "(I)Ljava/lang/String;") ||
          name == "trim" ||
          name == "length" ||
          (name == "lastIndexOf" && (descriptor == "(I)I" || descriptor == "(Ljava/lang/String;)I")) ||
          name == "toCharArray" ||
          name == "equals" ||
          name == "equalsIgnoreCase" ||
          (name == "startsWith" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "endsWith" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "getBytes" && (descriptor == "()[B" || descriptor == "(Ljava/lang/String;)[B" || descriptor == "(Ljava/nio/charset/Charset;)[B")) ||
          (name == "matches" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "contains" && descriptor == "(Ljava/lang/CharSequence;)Z") ||
          (name == "isEmpty" && descriptor == "()Z"))
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        STRING_DEFENSOR.toInternalName(),
        name,
        descriptor.stringDescriptor(),
        isInterface
      )
      return
    }

    if (opcode == Opcodes.INVOKESTATIC) {

      // String.format()
      if (owner == STRING_CLASS.toInternalName() && name == "format") {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          STRING_DEFENSOR.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }

      // parseXXX
      if (
        (owner == BYTE_CLASS.toInternalName() && name == "parseByte" && descriptor == "(Ljava/lang/String;)B") ||
        (owner == SHORT_CLASS.toInternalName() && name == "parseShort" && descriptor == "(Ljava/lang/String;)S") ||
        (owner == INTEGER_CLASS.toInternalName() && name == "parseInt" && descriptor == "(Ljava/lang/String;)I") ||
        (owner == LONG_CLASS.toInternalName() && name == "parseLong" && descriptor == "(Ljava/lang/String;)J") ||
        (owner == FLOAT_CLASS.toInternalName() && name == "parseFloat") ||
        (owner == DOUBLE_CLASS.toInternalName() && name == "parseDouble") ||
        (owner == BOOLEAN_CLASS.toInternalName() && name == "parseBoolean")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          PRIMITIVE_TYPE_PARSER.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }

      // View.inflate
      if (owner == Type.getInternalName(View::class.java) && name == "inflate" && descriptor == "(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;") {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          VIEW_DEFENSOR.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }

      // ContextCompat
      if (owner == CONTEXT_COMPAT_CLASS.toInternalName()) {
        if (name == "checkSelfPermission" && descriptor == "(Landroid/content/Context;Ljava/lang/String;)I") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            "checkSelfPermissionWithContextCompat",
            descriptor,
            isInterface
          )
          return
        }
      }

    }

    if (opcode == Opcodes.INVOKESPECIAL) {

      // JSONObject
      if (owner == JSONOBJECT_CLASS.toInternalName() && name == "<init>") {
        super.visitMethodInsn(
          opcode,
          SAFE_JSON_OBJECT.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }

      // JSONArray
      if (owner == JSONARRAY_CLASS.toInternalName() && name == "<init>") {
        super.visitMethodInsn(
          opcode,
          SAFE_JSON_ARRAY.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }
    }

    // List
    if ((opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == LIST_CLASS.toInternalName() ||
          owner == ARRAY_LIST_CLASS.toInternalName() ||
          owner == LINKED_LIST_CLASS.toInternalName() ||
          owner == COPY_ON_WRITE_ARRAY_LIST_CLASS.toInternalName()) &&
      (name == "size" ||
          name == "get" ||
          name == "add" ||
          name == "remove" ||
          name == "addAll" ||
          name == "clear")
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        name,
        descriptor.listDescriptor(),
        isInterface
      )
      return
    }

    // Map
    if ((opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == MAP_CLASS.toInternalName() ||
          owner == ARRAYMAP_CLASS.toInternalName() ||
          owner == HASHMAP_CLASS.toInternalName() ||
          owner == CONCURRENT_HASHMAP_CLASS.toInternalName()) &&
      (name == "size" ||
          name == "put" ||
          name == "get" ||
          name == "clear" ||
          name == "containsKey" ||
          name == "containsValue" ||
          name == "putAll")
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        name,
        descriptor.mapDescriptor(),
        isInterface
      )
      return
    }

    // File
    if (owner == Type.getInternalName(File::class.java)) {
      val fileDescriptor = Type.getDescriptor(File::class.java)
      if (opcode == Opcodes.INVOKESPECIAL && name == "<init>") {
        super.visitMethodInsn(opcode, SAFE_FILE.toInternalName(), name, descriptor, isInterface)
        return
      }

      if (opcode == Opcodes.INVOKEVIRTUAL) {
        if (
          (name == "getName" && descriptor == "()Ljava/lang/String;") ||
          (name == "getParent" && descriptor == "()Ljava/lang/String;") ||
          (name == "getParentFile" && descriptor == "()Ljava/io/File;") ||
          (name == "getPath" && descriptor == "()Ljava/lang/String;") ||
          (name == "isAbsolute" && descriptor == "()Z") ||
          (name == "getAbsolutePath" && descriptor == "()Ljava/lang/String;") ||
          (name == "getAbsoluteFile" && descriptor == "()Ljava/io/File;") ||
          (name == "getCanonicalPath" && descriptor == "()Ljava/lang/String;") ||
          (name == "getCanonicalFile" && descriptor == "()Ljava/io/File;") ||
          (name == "toURL" && descriptor == "()Ljava/net/URL;") ||
          (name == "toURI" && descriptor == "()Ljava/net/URI;") ||
          (name == "canRead" && descriptor == "()Z") ||
          (name == "canWrite" && descriptor == "()Z") ||
          (name == "exists" && descriptor == "()Z") ||
          (name == "isDirectory" && descriptor == "()Z") ||
          (name == "isFile" && descriptor == "()Z") ||
          (name == "isHidden" && descriptor == "()Z") ||
          (name == "lastModified" && descriptor == "()J") ||
          (name == "length" && descriptor == "()J") ||
          (name == "createNewFile" && descriptor == "()Z") ||
          (name == "delete" && descriptor == "()Z") ||
          (name == "deleteOnExit" && descriptor == "()V") ||
          (name == "list" && descriptor == "()[Ljava/lang/String;") ||
          (name == "list" && descriptor == "(Ljava/io/FilenameFilter;)[Ljava/lang/String;") ||
          (name == "listFiles" && descriptor == "()[Ljava/io/File;") ||
          (name == "listFiles" && descriptor == "(Ljava/io/FilenameFilter;)[Ljava/io/File;") ||
          (name == "listFiles" && descriptor == "(Ljava/io/FileFilter;)[Ljava/io/File;") ||
          (name == "mkdir" && descriptor == "()Z") ||
          (name == "mkdirs" && descriptor == "()Z") ||
          (name == "renameTo" && descriptor == "(Ljava/io/File;)Z") ||
          (name == "setLastModified" && descriptor == "(J)Z") ||
          (name == "setReadOnly" && descriptor == "()Z") ||
          (name == "setWritable" && descriptor == "(ZZ)Z") ||
          (name == "setWritable" && descriptor == "(Z)Z") ||
          (name == "setReadable" && descriptor == "(ZZ)Z") ||
          (name == "setReadable" && descriptor == "(Z)Z") ||
          (name == "setExecutable" && descriptor == "(ZZ)Z") ||
          (name == "setExecutable" && descriptor == "(Z)Z") ||
          (name == "canExecute" && descriptor == "()Z") ||
          (name == "getTotalSpace" && descriptor == "()J") ||
          (name == "getFreeSpace" && descriptor == "()J") ||
          (name == "getUsableSpace" && descriptor == "()J") ||
          (name == "toString" && descriptor == "()Ljava/lang/String;") ||
          (name == "toPath" && descriptor == "()Ljava/nio/file/Path;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FILE_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(fileDescriptor),
            isInterface
          )
          return
        }
      }
    }

    // Throwable
    if (opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "getMessage" && descriptor == "()Ljava/lang/String;") ||
        (name == "toString" && descriptor == "()Ljava/lang/String;") ||
        (name == "printStackTrace" && descriptor == "()V") ||
        (name == "printStackTrace" && descriptor == "(Ljava/io/PrintStream;)V") ||
        (name == "printStackTrace" && descriptor == "(Ljava/io/PrintWriter;)V")
      ) {
        try {
          if (java.lang.Throwable::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              THROWABLE_DEFENSOR.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor(Type.getDescriptor(java.lang.Throwable::class.java)),
              isInterface
            )
            return
          }
        } catch (ignored: Throwable) {
        }
      }
    }

    if (opcode == Opcodes.INVOKEVIRTUAL) {
      // Intent
      if (owner == INTENT_CLASS.toInternalName() &&
        (name.matches(Regex("get(([a-zA-Z]+Extra)|Extras)")) || (name == "putExtra" && descriptor == DESCRIPTOR_PUT_EXTRA))
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          INTENT_DEFENSOR.toInternalName(),
          name,
          descriptor.intentDescriptor(),
          isInterface
        )
        return
      }

      // View#setVisibility
      if (name == "setVisibility") {
        try {
          if (View::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              VIEW_DEFENSOR.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor(Type.getDescriptor(View::class.java)),
              isInterface
            )
            return
          }
        } catch (ignored: ClassNotFoundException) {
        }
      }

      // WebView
      if (owner == WEBVIEW_CLASS.toInternalName()) {
        if (
          (name == "loadUrl" && descriptor == "(Ljava/lang/String;)V") ||
          (name == "loadData" && descriptor == "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            VIEW_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/webkit/WebView;"),
            isInterface
          )
          return
        }
      }

      // RecyclerView
      if (owner == RECYCLER_CLASS.toInternalName()) {
        if (
          (name == "bindViewToPosition" && descriptor == "(Landroid/view/View;I)V") ||
          (name == "getViewForPosition" && descriptor == "(I)Landroid/view/View;") ||
          (name == "convertPreLayoutPositionToPostLayout" && descriptor == "(I)I")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            VIEW_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroidx/recyclerview/widget/RecyclerView\$Recycler"),
            isInterface
          )
          return
        }
      }

      // TextView#setText
      if (
        (name == "setText" && (descriptor == "(Ljava/lang/CharSequence;)V" || descriptor == "(I)V" || descriptor == "(ILandroid/widget/TextView\$BufferType;)V" || descriptor == "([CII)V")) ||
        (name == "setHint" && (descriptor == "(Ljava/lang/CharSequence;)V" || descriptor == "(I)V"))
      ) {
        try {
          if (TextView::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              TEXTVIEW_DEFENSOR.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor(Type.getDescriptor(TextView::class.java)),
              isInterface
            )
            return
          }
        } catch (ignored: ClassNotFoundException) {
        }
      }

      // Uri
      if (owner == URI_CLASS.toInternalName() && (name == "getQueryParameterNames" || name == "getQueryParameters" || name == "getQueryParameter" || name == "getBooleanQueryParameter")) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          URI_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroid/net/Uri;"),
          isInterface
        )
        return
      }

      // activity
      if (owner == ACTIVITY_CLASS.toInternalName()) {
        if (name == "isDestroyed" || name == "isChangingConfigurations") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            ACTIVITY_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(Activity::class.java)),
            isInterface
          )
          return
        }
      }

      // Fragment
      if (
        (name == "getContext" && descriptor == "()Landroid/content/Context;") ||
        (name == "getActivity" && descriptor == "()Landroid/app/Activity;") ||
        (name == "isAdded" && descriptor == "()Z") ||
        (name == "isDetached" && descriptor == "()Z") ||
        (name == "isRemoving" && descriptor == "()Z") ||
        (name == "isInLayout" && descriptor == "()Z") ||
        (name == "isResumed" && descriptor == "()Z") ||
        (name == "isVisible" && descriptor == "()Z") ||
        (name == "isHidden" && descriptor == "()Z") ||
        (name == "startActivity" && (descriptor == "(Landroid/content/Intent;)V" || descriptor == "(Landroid/content/Intent;Landroid/os/Bundle;)V")) ||
        (name == "startActivityForResult" && (descriptor == "(Landroid/content/Intent;I)V" || descriptor == "(Landroid/content/Intent;ILandroid/os/Bundle;)V")) ||
        (name == "getView" && descriptor == "()Landroid/view/View;")
      ) {
        if (owner == Type.getInternalName(Fragment::class.java)) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENT_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(Fragment::class.java)),
            isInterface
          )
          return
        } else if (owner == Type.getInternalName(androidx.fragment.app.Fragment::class.java)) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENTX_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(androidx.fragment.app.Fragment::class.java)),
            isInterface
          )
          return
        }
      }

      if (owner == FRAGMENT_MANAGER_CLASS.toInternalName()) {
        if (
          (name == "popBackStack" && descriptor == "()V") ||
          (name == "putFragment" && descriptor == "(Landroid/os/Bundle;Ljava/lang/String;Landroidx/fragment/app/Fragment;)V") ||
          (name == "getFragment" && descriptor == "(Landroid/os/Bundle;Ljava/lang/String;)Landroidx/fragment/app/Fragment;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENT_MANAGER_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(androidx.fragment.app.FragmentManager::class.java)),
            isInterface
          )
          return
        }
      }

      // Context
      if (owner == CONTEXT_CLASS.toInternalName()) {
        if (
          (name == "getPackageName" && descriptor == "()Ljava/lang/String;") ||
          (name == "getSystemService" && descriptor == "(Ljava/lang/String;)Ljava/lang/Object;") ||
          (name == "getFilesDir" && descriptor == "()Ljava/io/File;") ||
          (name == "bindService" && descriptor == "(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z") ||
          (name == "checkPermission" && descriptor == "(Ljava/lang/String;II)I") ||
          (name == "checkCallingPermission" && descriptor == "(Ljava/lang/String;)I") ||
          (name == "checkSelfPermission" && descriptor == "(Ljava/lang/String;)I") ||
          (name == "checkCallingOrSelfPermission" && descriptor == "(Ljava/lang/String;)I")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            CONTEXT_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/content/Context;"),
            isInterface
          )
          return
        }
      }

      if (
        (name == "sendBroadcast" && descriptor == "(Landroid/content/Intent;)V") ||
        (name == "registerReceiver" && descriptor == "(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;") ||
        (name == "unregisterReceiver" && descriptor == "(Landroid/content/BroadcastReceiver;)V")
      ) {
        try {
          if (Context::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor("Landroid/content/Context;"),
              isInterface
            )
            return
          }
        } catch (ignored: Throwable) {
        }
      }


      if (owner == PACKAGE_MANAGER_CLASS.toInternalName()) {
        if (
          (name == "getPackageInfo" && descriptor == "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;") ||
          (name == "getApplicationInfo" && descriptor == "(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/content/pm/PackageManager;"),
            isInterface
          )
          return
        }
      }

      if (owner == WINDOW_MANAGER_CLASS.toInternalName()) {
        if (name == "getDefaultDisplay" && descriptor == "()Landroid/view/Display;") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/view/WindowManager;"),
            isInterface
          )
          return
        }
      }

      if (owner == DISPLAY_CLASS.toInternalName()) {
        if (name == "getSize" && descriptor == "(Landroid/graphics/Point;)V") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            "getDisplaySize",
            descriptor.convertToStaticDescriptor("Landroid/view/Display;"),
            isInterface
          )
          return
        }
        if (name == "getRefreshRate" && descriptor == "()F") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            "getDisplayRefreshRate",
            descriptor.convertToStaticDescriptor("Landroid/view/Display;"),
            isInterface
          )
          return
        }
        if (name == "getMetrics" && descriptor == "(Landroid/util/DisplayMetrics;)V") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            "getDisplayMetrics",
            descriptor.convertToStaticDescriptor("Landroid/view/Display;"),
            isInterface
          )
          return
        }
      }

      if (owner == CONNECTIVITY_MANAGER_CLASS.toInternalName()) {
        if (name == "getActiveNetworkInfo" && descriptor == "()Landroid/net/NetworkInfo;") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/net/ConnectivityManager;"),
            isInterface
          )
          return
        }
      }

      if (owner == ACTIVITY_MANAGER_CLASS.toInternalName()) {
        if (name == "getRunningAppProcesses" && descriptor == "()Ljava/util/List;") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/app/ActivityManager;"),
            isInterface
          )
          return
        }
      }

      if (owner == TELEPHONY_MANAGER_CLASS.toInternalName()) {
        if (
          (name == "getMeid" && (descriptor == "()Ljava/lang/String;" || descriptor == "(I)Ljava/lang/String;")) ||
          (name == "getDeviceId" && (descriptor == "()Ljava/lang/String;" || descriptor == "(I)Ljava/lang/String;")) ||
          (name == "getSubscriberId" && descriptor == "()Ljava/lang/String;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            DEAD_OBJECT_CRASH_HANDLER.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/telephony/TelephonyManager;"),
            isInterface
          )
          return
        }
      }

      if (owner == PAINT_CLASS.toInternalName()) {
        if (
          (name == "measureText" && (descriptor == "([CII)F" || descriptor == "(Ljava/lang/String;II)F" || descriptor == "(Ljava/lang/String;)F" || descriptor == "(Ljava/lang/CharSequence;II)F")) ||
          (name == "getTextBounds" && (descriptor == "(Ljava/lang/String;IILandroid/graphics/Rect;)V" || descriptor == "(Ljava/lang/CharSequence;IILandroid/graphics/Rect;)V" || descriptor == "([CIILandroid/graphics/Rect;)V"))
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            PAINT_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/graphics/Paint;"),
            isInterface
          )
          return
        }
      }

    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}
