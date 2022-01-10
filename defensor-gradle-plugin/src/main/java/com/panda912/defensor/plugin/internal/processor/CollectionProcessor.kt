package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/14 16:28
 */
class CollectionProcessor : BytecodeProcessor {

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
        return CollectionMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class CollectionMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  // Array
//  override fun visitInsn(opcode: Int) {
//
//    val descriptor = when (opcode) {
//      Opcodes.IALOAD -> "([II)I"
//      Opcodes.LALOAD -> "([JI)J"
//      Opcodes.FALOAD -> "([FI)F"
//      Opcodes.DALOAD -> "([DI)D"
//      Opcodes.CALOAD -> "([CI)C"
//      Opcodes.SALOAD -> "([SI)S"
//      else -> null
//    }
//
//    if (descriptor.isNullOrEmpty()) {
//      super.visitInsn(opcode)
//    } else {
//      super.visitMethodInsn(
//        Opcodes.INVOKESTATIC,
//        COLLECTION_DEFENSOR.toInternalName(),
//        "get",
//        descriptor,
//        false
//      )
//    }
//  }

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String,
    isInterface: Boolean
  ) {

    // List
    if (
      (opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == LIST_CLASS.toInternalName() ||
          owner == ARRAY_LIST_CLASS.toInternalName() ||
          owner == LINKED_LIST_CLASS.toInternalName() ||
          owner == COPY_ON_WRITE_ARRAY_LIST_CLASS.toInternalName()) &&
      ((name == "size" && descriptor == "()I") ||
          (name == "get" && descriptor == "(I)Ljava/lang/Object;") ||
          (name == "add" && (descriptor == "(Ljava/lang/Object;)Z" || descriptor == "(ILjava/lang/Object;)V")) ||
          (name == "remove" && (descriptor == "(I)Ljava/lang/Object;" || descriptor == "(Ljava/lang/Object;)Z")) ||
          (name == "addAll" && (descriptor == "(Ljava/util/Collection;)Z" || descriptor == "(ILjava/util/Collection;)Z")) ||
          (name == "clear" && descriptor == "()V"))
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
    if (
      (opcode == Opcodes.INVOKEINTERFACE || opcode == Opcodes.INVOKEVIRTUAL) &&
      (owner == MAP_CLASS.toInternalName() ||
          owner == ARRAYMAP_CLASS.toInternalName() ||
          owner == HASHMAP_CLASS.toInternalName() ||
          owner == CONCURRENT_HASHMAP_CLASS.toInternalName()) &&
      ((name == "size" && descriptor == "()I") ||
          (name == "put" && descriptor == "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;") ||
          (name == "get" && descriptor == "(Ljava/lang/Object;)Ljava/lang/Object;") ||
          (name == "clear" && descriptor == "()V") ||
          (name == "containsKey" && descriptor == "(Ljava/lang/Object;)Z") ||
          (name == "containsValue" && descriptor == "(Ljava/lang/Object;)Z") ||
          (name == "putAll" && descriptor == "(Ljava/util/Map;)V"))
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