package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.GRID_LAYOUT_MANAGER_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/22 09:46
 */
class GridLayoutManagerInterceptor : BytecodeInterceptor {

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
        return GridLayoutManagerMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
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