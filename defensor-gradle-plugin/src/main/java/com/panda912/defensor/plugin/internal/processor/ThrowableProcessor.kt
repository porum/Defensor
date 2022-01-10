package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.THROWABLE_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toClassName
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created by panda on 2021/9/15 17:52
 */
class ThrowableProcessor : BytecodeProcessor {

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
        return ThrowableMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class ThrowableMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

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

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}