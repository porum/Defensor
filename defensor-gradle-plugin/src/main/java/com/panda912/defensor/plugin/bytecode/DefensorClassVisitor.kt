package com.panda912.defensor.plugin.bytecode

import com.panda912.defensor.plugin.utils.*
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

    // DialogFragment
    if (superName == DIALOG_FRAGMENT_CLASS.toInternalName()) {
      super.visit(version, access, name, signature, SAFE_DIALOG_FRAGMENT.toInternalName(), interfaces)
      return
    }

//    // MutableLiveData
//    if (superName == MUTABLE_LIVE_DATA_CLASS.toInternalName()) {
//      super.visit(version, access, name, signature, SAFE_MUTABLE_LIVE_DATA.toInternalName(), interfaces)
//      return
//    }

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