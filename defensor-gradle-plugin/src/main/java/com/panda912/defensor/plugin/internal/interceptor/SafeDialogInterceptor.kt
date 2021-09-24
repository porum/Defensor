package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.BytecodeInterceptor.Chain
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.ClassWriter

/**
 * Created by panda on 2021/9/14 16:27
 */
class SafeDialogInterceptor : BytecodeInterceptor {

  override fun intercept(chain: Chain): Output {
    val input = chain.request()

    val cr = BaseClassReader(input.bytes)
    val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
    cr.accept(SafeDialogClassVisitor(cw))
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}


/**
 * Created by panda on 2021/9/15 10:54
 */
class SafeDialogClassVisitor(cw: ClassWriter) : BaseClassVisitor(cw) {

  override fun visit(
    version: Int,
    access: Int,
    name: String?,
    signature: String?,
    superName: String?,
    interfaces: Array<out String>?
  ) {

    val replacedSuperName = when (superName) {
      DIALOG_CLASS.toInternalName() -> SAFE_DIALOG.toInternalName()
      DIALOG_FRAGMENT_CLASS.toInternalName() -> SAFE_DIALOG_FRAGMENT.toInternalName()
      else -> superName
    }

    super.visit(version, access, name, signature, replacedSuperName, interfaces)
  }

}