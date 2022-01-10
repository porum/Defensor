package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.UNBOXING_DEFENSOR
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
 * Created by panda on 2021/9/15 11:19
 */
class UnboxingProcessor : BytecodeProcessor {

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
        return UnboxingMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class UnboxingMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {
  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

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

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}