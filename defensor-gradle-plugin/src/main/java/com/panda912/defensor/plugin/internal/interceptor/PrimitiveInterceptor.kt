package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 11:20
 */
class PrimitiveInterceptor : BytecodeInterceptor {

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
        return PrimitiveMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class PrimitiveMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (opcode == Opcodes.INVOKESTATIC) {
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
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}