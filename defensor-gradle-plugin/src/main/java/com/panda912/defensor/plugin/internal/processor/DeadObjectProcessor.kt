package com.panda912.defensor.plugin.internal.processor

import android.content.Context
import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 18:06
 */
class DeadObjectProcessor : BytecodeProcessor {

  override fun process(chain: BytecodeProcessor.Chain): Output {
    val input = chain.input()
    input.classVisitor = object : BaseClassVisitor(input.classVisitor) {
      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
      ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return DeadObjectMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class DeadObjectMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (opcode == Opcodes.INVOKEVIRTUAL) {

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
        if (
          (name == "getRunningAppProcesses" && descriptor == "()Ljava/util/List;") ||
          (name == "getRunningServices" && descriptor == "(I)Ljava/util/List;") ||
          (name == "getMemoryInfo" && descriptor == "(Landroid/app/ActivityManager\$MemoryInfo;)V")
        ) {
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
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}