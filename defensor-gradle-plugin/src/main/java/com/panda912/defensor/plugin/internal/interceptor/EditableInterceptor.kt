package com.panda912.defensor.plugin.internal.interceptor

import android.text.Editable
import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.EDITABLE_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toClassName
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2022/2/22 15:59
 */
class EditableInterceptor : BytecodeInterceptor {

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
        return EditableMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

private class EditableMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (
      (name == "replace" && descriptor == "(IILjava/lang/CharSequence;II)Landroid/text/Editable;") ||
      (name == "replace" && descriptor == "(IILjava/lang/CharSequence;)Landroid/text/Editable;") ||
      (name == "insert" && descriptor == "(ILjava/lang/CharSequence;II)Landroid/text/Editable;") ||
      (name == "insert" && descriptor == "(ILjava/lang/CharSequence;)Landroid/text/Editable;") ||
      (name == "delete" && descriptor == "(II)Landroid/text/Editable;") ||
      (name == "append" && descriptor == "(Ljava/lang/CharSequence;)Landroid/text/Editable;") ||
      (name == "append" && descriptor == "(Ljava/lang/CharSequence;II)Landroid/text/Editable;") ||
      (name == "append" && descriptor == "(C)Landroid/text/Editable;") ||
      (name == "replace" && descriptor == "(IILjava/lang/CharSequence;II)Landroid/text/SpannableStringBuilder;") ||
      (name == "replace" && descriptor == "(IILjava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;") ||
      (name == "insert" && descriptor == "(ILjava/lang/CharSequence;II)Landroid/text/SpannableStringBuilder;") ||
      (name == "insert" && descriptor == "(ILjava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;") ||
      (name == "delete" && descriptor == "(II)Landroid/text/SpannableStringBuilder;") ||
      (name == "append" && descriptor == "(Ljava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;") ||
      (name == "append" && descriptor == "(Ljava/lang/CharSequence;II)Landroid/text/SpannableStringBuilder;") ||
      (name == "append" && descriptor == "(C)Landroid/text/SpannableStringBuilder;")
    ) {
      try {
        if (Editable::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
          val newDescriptor = descriptor.convertToStaticDescriptor("Landroid/text/Editable;")
            .replace("Landroid/text/SpannableStringBuilder;", "Landroid/text/Editable;")
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            EDITABLE_DEFENSOR.toInternalName(),
            name,
            newDescriptor,
            isInterface
          )
          return
        }
      } catch (ignored: ClassNotFoundException) {
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}