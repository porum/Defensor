package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.VALUE_ANIMATOR_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/22 09:45
 */
class ValueAnimatorProcessor : BytecodeProcessor {

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
        return ValueAnimatorMethodVisitor(mv)
      }
    }
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