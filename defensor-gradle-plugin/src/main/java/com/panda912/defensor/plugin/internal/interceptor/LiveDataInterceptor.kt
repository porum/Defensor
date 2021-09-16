package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.LIVE_DATA_DEFENSOR
import com.panda912.defensor.plugin.utils.MUTABLE_LIVE_DATA_CLASS
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 18:23
 */
class LiveDataInterceptor : BytecodeInterceptor {
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
        return LiveDataMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class LiveDataMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == MUTABLE_LIVE_DATA_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "observe" && descriptor == "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Observer;)V") ||
        (name == "observeForever" && descriptor == "(Landroidx/lifecycle/Observer;)V")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          LIVE_DATA_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor("Landroidx/lifecycle/LiveData;"),
          isInterface
        )
        return
      }
    }
    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}