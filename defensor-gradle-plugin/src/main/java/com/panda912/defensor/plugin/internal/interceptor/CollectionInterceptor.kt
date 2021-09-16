package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/14 16:28
 */
class CollectionInterceptor : BytecodeInterceptor {

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
        return CollectionMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class CollectionMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitInsn(opcode: Int) {

    val descriptor = when (opcode) {
      Opcodes.IALOAD -> "([II)I"
      Opcodes.LALOAD -> "([JI)J"
      Opcodes.FALOAD -> "([FI)F"
      Opcodes.DALOAD -> "([DI)D"
      Opcodes.CALOAD -> "([CI)C"
      Opcodes.SALOAD -> "([SI)S"
      else -> null
    }

    if (descriptor.isNullOrEmpty()) {
      super.visitInsn(opcode)
    } else {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        "get",
        descriptor,
        false
      )
    }
  }

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String,
    isInterface: Boolean
  ) {

    // List
    if ((opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == LIST_CLASS.toInternalName() ||
          owner == ARRAY_LIST_CLASS.toInternalName() ||
          owner == LINKED_LIST_CLASS.toInternalName() ||
          owner == COPY_ON_WRITE_ARRAY_LIST_CLASS.toInternalName()) &&
      (name == "size" ||
          name == "get" ||
          name == "add" ||
          name == "remove" ||
          name == "addAll" ||
          name == "clear")
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        name,
        descriptor.listDescriptor(),
        isInterface
      )
      return
    }

    // Map
    if ((opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == MAP_CLASS.toInternalName() ||
          owner == ARRAYMAP_CLASS.toInternalName() ||
          owner == HASHMAP_CLASS.toInternalName() ||
          owner == CONCURRENT_HASHMAP_CLASS.toInternalName()) &&
      (name == "size" ||
          name == "put" ||
          name == "get" ||
          name == "clear" ||
          name == "containsKey" ||
          name == "containsValue" ||
          name == "putAll")
    ) {
      super.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        COLLECTION_DEFENSOR.toInternalName(),
        name,
        descriptor.mapDescriptor(),
        isInterface
      )
      return
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}