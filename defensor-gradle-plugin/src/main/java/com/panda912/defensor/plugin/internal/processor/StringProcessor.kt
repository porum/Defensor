package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 11:21
 */
class StringProcessor : BytecodeProcessor {

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
        return StringMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class StringMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    // String
    if (owner == STRING_CLASS.toInternalName()) {
      if (opcode == Opcodes.INVOKESTATIC) {
        if (name == "format") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            STRING_DEFENSOR.toInternalName(),
            name,
            descriptor,
            isInterface
          )
          return
        }
      }

      if (opcode == Opcodes.INVOKEVIRTUAL) {
        if (
          (name == "substring" && descriptor == "(I)Ljava/lang/String;") ||
          name == "trim" ||
          name == "length" ||
          (name == "lastIndexOf" && (descriptor == "(I)I" || descriptor == "(Ljava/lang/String;)I")) ||
          name == "toCharArray" ||
          name == "equals" ||
          name == "equalsIgnoreCase" ||
          (name == "startsWith" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "endsWith" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "getBytes" && (descriptor == "()[B" || descriptor == "(Ljava/lang/String;)[B" || descriptor == "(Ljava/nio/charset/Charset;)[B")) ||
          (name == "matches" && descriptor == "(Ljava/lang/String;)Z") ||
          (name == "contains" && descriptor == "(Ljava/lang/CharSequence;)Z") ||
          (name == "isEmpty" && descriptor == "()Z")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            STRING_DEFENSOR.toInternalName(),
            name,
            descriptor.stringDescriptor(),
            isInterface
          )
          return
        }
      }
    }

    // CharSequence
    if (owner == CHAR_SEQUENCE_CLASS.toInternalName()) {
      if (opcode == Opcodes.INVOKEINTERFACE) {
        if (name == "length" || name == "charAt" || name == "subSequence" || name == "toString") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            STRING_DEFENSOR.toInternalName(),
            name,
            descriptor.charSequenceDescriptor(),
            isInterface
          )
          return
        }
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}