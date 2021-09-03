package com.panda912.defensor.plugin.bytecode

import com.panda912.defensor.plugin.utils.DIALOG_CLASS
import com.panda912.defensor.plugin.utils.SAFE_DIALOG
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/8/13 17:33
 */
class DefensorClassVisitor(classWriter: ClassWriter) : ClassVisitor(Opcodes.ASM7, classWriter) {

  override fun visit(
    version: Int,
    access: Int,
    name: String?,
    signature: String?,
    superName: String?,
    interfaces: Array<out String>?
  ) {

    // Dialog
    if (superName == DIALOG_CLASS.toInternalName()) {
      super.visit(version, access, name, signature, SAFE_DIALOG.toInternalName(), interfaces)
      return
    }

    super.visit(version, access, name, signature, superName, interfaces)
  }

  override fun visitMethod(
    access: Int,
    name: String,
    descriptor: String,
    signature: String?,
    exceptions: Array<out String>?
  ): MethodVisitor {
    return DefensorMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions))
  }
}