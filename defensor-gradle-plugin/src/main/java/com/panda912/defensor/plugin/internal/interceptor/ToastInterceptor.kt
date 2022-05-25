package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.TOAST_COMPAT
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2022/5/24 19:00
 */
class ToastInterceptor : BytecodeInterceptor {

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
        return ToastMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class ToastMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (owner == "android/widget/Toast" && name == "show" && opcode == Opcodes.INVOKEVIRTUAL && descriptor == "()V") {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        TOAST_COMPAT.toInternalName(),
        name,
        descriptor.convertToStaticDescriptor("Landroid/widget/Toast;"),
        isInterface
      )
      return
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}