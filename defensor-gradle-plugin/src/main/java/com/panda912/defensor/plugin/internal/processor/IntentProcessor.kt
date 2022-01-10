package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 17:55
 */
class IntentProcessor : BytecodeProcessor {

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
        return IntentMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class IntentMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (owner == INTENT_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (name.matches(Regex("get(([a-zA-Z]+Extra)|Extras)")) ||
        (name == "putExtra" && descriptor == DESCRIPTOR_PUT_EXTRA)
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          INTENT_DEFENSOR.toInternalName(),
          name,
          descriptor.intentDescriptor(),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}