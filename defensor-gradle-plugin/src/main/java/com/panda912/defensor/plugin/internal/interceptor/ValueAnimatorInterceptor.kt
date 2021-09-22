package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.VALUE_ANIMATOR_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/22 09:45
 */
class ValueAnimatorInterceptor : BytecodeInterceptor {

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
        return ValueAnimatorMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class ValueAnimatorMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == "android/animation/ValueAnimator" && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "start" && descriptor == "()V") ||
        (name == "end" && descriptor == "()V") ||
        (name == "cancel" && descriptor == "()V") ||
        (name == "resume" && descriptor == "()V") ||
        (name == "reverse" && descriptor == "()V") ||
        (name == "setDuration" && descriptor == "(J)Landroid/animation/ValueAnimator;")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          VALUE_ANIMATOR_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroid/animation/ValueAnimator;"),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}