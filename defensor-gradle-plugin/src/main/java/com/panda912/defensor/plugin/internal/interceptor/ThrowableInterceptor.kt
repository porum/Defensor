package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.THROWABLE_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toClassName
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 17:52
 */
class ThrowableInterceptor : BytecodeInterceptor {

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
        return ThrowableMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
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