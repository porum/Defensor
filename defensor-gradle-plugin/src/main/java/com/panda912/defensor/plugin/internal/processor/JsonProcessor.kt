package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 17:27
 */
class JsonProcessor : BytecodeProcessor {

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
        return JsonMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class JsonMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitTypeInsn(opcode: Int, type: String?) {
    if (opcode == Opcodes.NEW) {
      if (type == JSONOBJECT_CLASS.toInternalName()) {
        super.visitTypeInsn(opcode, SAFE_JSON_OBJECT.toInternalName())
        return
      }
      if (type == JSONARRAY_CLASS.toInternalName()) {
        super.visitTypeInsn(opcode, SAFE_JSON_ARRAY.toInternalName())
        return
      }
    }

    super.visitTypeInsn(opcode, type)
  }

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (opcode == Opcodes.INVOKESPECIAL) {
      // JSONObject
      if (owner == JSONOBJECT_CLASS.toInternalName() && name == "<init>") {
        super.visitMethodInsn(
          opcode,
          SAFE_JSON_OBJECT.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }

      // JSONArray
      if (owner == JSONARRAY_CLASS.toInternalName() && name == "<init>") {
        super.visitMethodInsn(
          opcode,
          SAFE_JSON_ARRAY.toInternalName(),
          name,
          descriptor,
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}