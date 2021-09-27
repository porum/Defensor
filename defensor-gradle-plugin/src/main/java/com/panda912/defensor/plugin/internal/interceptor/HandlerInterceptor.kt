package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.HANDLER_CLASS
import com.panda912.defensor.plugin.utils.HANDLER_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/27 15:51
 */
class HandlerInterceptor : BytecodeInterceptor {

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
        return HandlerMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

private class HandlerMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {
  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == HANDLER_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "post" && descriptor == "(Ljava/lang/Runnable;)Z") ||
        (name == "postDelayed" && descriptor == "(Ljava/lang/Runnable;J)Z") ||
        (name == "removeCallbacks" && descriptor == "(Ljava/lang/Runnable;)V") ||
        (name == "removeCallbacks" && descriptor == "(Ljava/lang/Runnable;Ljava/lang/Object;)V") ||
        (name == "sendMessage" && descriptor == "(Landroid/os/Message;)Z") ||
        (name == "removeMessages" && descriptor == "(I)V") ||
        (name == "removeMessages" && descriptor == "(ILjava/lang/Object;)V") ||
        (name == "removeCallbacksAndMessages" && descriptor == "(Ljava/lang/Object;)V") ||
        (name == "hasMessages" && descriptor == "(I)Z") ||
        (name == "hasMessages" && descriptor == "(ILjava/lang/Object;)Z") ||
        (name == "hasCallbacks" && descriptor == "(Ljava/lang/Runnable;)Z")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          HANDLER_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroid/os/Handler;"),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}