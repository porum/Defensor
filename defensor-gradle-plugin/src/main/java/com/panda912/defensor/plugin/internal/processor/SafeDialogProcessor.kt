package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.BytecodeProcessor.Chain
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import org.objectweb.asm.ClassVisitor

/**
 * Created by panda on 2021/9/14 16:27
 */
class SafeDialogProcessor : BytecodeProcessor {

  override fun process(chain: Chain): Output {
    val input = chain.input()
    input.classVisitor = SafeDialogClassVisitor(input.classVisitor)
    return chain.proceed(input)
  }
}


/**
 * Created by panda on 2021/9/15 10:54
 */
class SafeDialogClassVisitor(classVisitor: ClassVisitor) : BaseClassVisitor(classVisitor) {

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