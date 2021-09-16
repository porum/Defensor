package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 18:13
 */
class ContextInterceptor : BytecodeInterceptor {

  override fun intercept(chain: BytecodeInterceptor.Chain): Output {
    val input = chain.request()

    val cr = BaseClassReader(input.bytes)
    val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
    cr.accept(object : BaseClassVisitor(cw) {
      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
      ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return ContextMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class ContextMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    // ContextCompat
    if (owner == CONTEXT_COMPAT_CLASS.toInternalName() && opcode == Opcodes.INVOKESTATIC) {
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

    // Context
    if (owner == CONTEXT_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
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

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}