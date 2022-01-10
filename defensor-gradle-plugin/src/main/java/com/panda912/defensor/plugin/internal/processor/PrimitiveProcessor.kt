package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 11:20
 */
class PrimitiveProcessor : BytecodeProcessor {

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
        return PrimitiveMethodVisitor(mv)
      }
    }
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