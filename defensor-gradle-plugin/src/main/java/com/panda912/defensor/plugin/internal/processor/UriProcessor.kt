package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.URI_CLASS
import com.panda912.defensor.plugin.URI_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 18:19
 */
class UriProcessor : BytecodeProcessor {

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
        return UriMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class UriMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (owner == URI_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        name == "getQueryParameterNames" ||
        name == "getQueryParameters" ||
        name == "getQueryParameter" ||
        name == "getBooleanQueryParameter"
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          URI_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroid/net/Uri;"),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}