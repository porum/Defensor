package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.GRID_LAYOUT_MANAGER_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/22 09:46
 */
class GridLayoutManagerProcessor : BytecodeProcessor {

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
        return GridLayoutManagerMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class GridLayoutManagerMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {
  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == "androidx/recyclerview/widget/GridLayoutManager" && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "setStackFromEnd" && descriptor == "(Z)V") ||
        (name == "setSpanCount" && descriptor == "(I)V")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          GRID_LAYOUT_MANAGER_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroidx/recyclerview/widget/GridLayoutManager;"),
          isInterface
        )
        return
      }
    }
    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}