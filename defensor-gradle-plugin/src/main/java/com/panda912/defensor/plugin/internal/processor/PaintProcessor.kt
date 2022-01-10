package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.PAINT_CLASS
import com.panda912.defensor.plugin.PAINT_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 18:25
 */
class PaintProcessor : BytecodeProcessor {
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
        return PaintMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class PaintMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == PAINT_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "measureText" && (descriptor == "([CII)F" || descriptor == "(Ljava/lang/String;II)F" || descriptor == "(Ljava/lang/String;)F" || descriptor == "(Ljava/lang/CharSequence;II)F")) ||
        (name == "getTextBounds" && (descriptor == "(Ljava/lang/String;IILandroid/graphics/Rect;)V" || descriptor == "(Ljava/lang/CharSequence;IILandroid/graphics/Rect;)V" || descriptor == "([CIILandroid/graphics/Rect;)V"))
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          PAINT_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroid/graphics/Paint;"),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}