package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.COLLECTION_DEFENSOR
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.IntegrateNodeTransformer
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

/**
 * Created by panda on 2021/9/26 10:50
 */

class ArrayProcessor : BytecodeProcessor {

  override fun process(chain: BytecodeProcessor.Chain): Output {
    val input = chain.input()
    val classVisitor = object : IntegrateNodeTransformer(input.classVisitor) {
      override fun process(source: ClassNode): ClassNode {
        for (methodNode in source.methods) {
          for (insnNode in methodNode.instructions.toArray()) {
            if (insnNode.type == AbstractInsnNode.INSN) {
              when (insnNode.opcode) {
                Opcodes.CALOAD,
                Opcodes.SALOAD,
                Opcodes.IALOAD,
                Opcodes.LALOAD,
                Opcodes.FALOAD,
                Opcodes.DALOAD -> handlePrimitiveType(methodNode, insnNode)
                Opcodes.AALOAD,
                Opcodes.BALOAD -> handleByteBooleanReferenceType(methodNode, insnNode)
              }
            }
          }
        }
        return source
      }
    }
    input.classVisitor = classVisitor
    return chain.proceed(input)
  }

  private fun handlePrimitiveType(methodNode: MethodNode, insnNode: AbstractInsnNode) {
    val descriptor = when (insnNode.opcode) {
      Opcodes.IALOAD -> "([II)I"
      Opcodes.LALOAD -> "([JI)J"
      Opcodes.FALOAD -> "([FI)F"
      Opcodes.DALOAD -> "([DI)D"
      Opcodes.CALOAD -> "([CI)C"
      Opcodes.SALOAD -> "([SI)S"
      else -> null
    }
    if (descriptor.isNullOrEmpty()) return
    val methodInsnNode = MethodInsnNode(
      Opcodes.INVOKESTATIC,
      COLLECTION_DEFENSOR.toInternalName(),
      "get",
      descriptor,
      false
    )
    methodNode.instructions.set(insnNode, methodInsnNode)
  }

  private fun handleByteBooleanReferenceType(methodNode: MethodNode, insnNode: AbstractInsnNode) {
    val prev = insnNode.previous
    if (
      prev?.opcode == Opcodes.ILOAD ||
      prev?.opcode == Opcodes.ICONST_0 ||
      prev?.opcode == Opcodes.ICONST_1 ||
      prev?.opcode == Opcodes.ICONST_2 ||
      prev?.opcode == Opcodes.ICONST_3 ||
      prev?.opcode == Opcodes.ICONST_4 ||
      prev?.opcode == Opcodes.ICONST_5 ||
      prev?.opcode == Opcodes.BIPUSH ||
      prev?.opcode == Opcodes.SIPUSH ||
      (prev?.opcode == Opcodes.LDC && prev.type == AbstractInsnNode.LDC_INSN && (prev as LdcInsnNode).cst is Int)
    ) {
      var arrayDescriptor: String? = null
      val prevPrev = prev.previous
      if (prevPrev?.opcode == Opcodes.ALOAD && prevPrev.type == AbstractInsnNode.VAR_INSN) {
        val aload = prevPrev as VarInsnNode
        val aloadIndex = methodNode.instructions.indexOf(aload)
        methodNode.localVariables?.filter { it.index == aload.`var` }?.forEach {
          val start = methodNode.instructions.indexOf(it.start)
          val end = methodNode.instructions.indexOf(it.end)
          if (aloadIndex != -1 && start != -1 && end != -1 && aloadIndex in start until end) {
            arrayDescriptor = it.desc
            return@forEach
          }
        }
      } else if ((prevPrev?.opcode == Opcodes.GETSTATIC || prevPrev?.opcode == Opcodes.GETFIELD) && prevPrev.type == AbstractInsnNode.FIELD_INSN) {
        val field = prevPrev as FieldInsnNode
        arrayDescriptor = field.desc
      }

      if (!arrayDescriptor.isNullOrEmpty()) {
        val type = Type.getType(arrayDescriptor)
        val dimensions = type.dimensions
        if (dimensions == 1) {
          if (insnNode.opcode == Opcodes.AALOAD) {
            val methodInsnNode = MethodInsnNode(
              Opcodes.INVOKESTATIC,
              COLLECTION_DEFENSOR.toInternalName(),
              "get",
              "([Ljava/lang/Object;I)Ljava/lang/Object;",
              false
            )
            methodNode.instructions.set(insnNode, methodInsnNode)
            methodNode.instructions.insert(
              methodInsnNode,
              TypeInsnNode(Opcodes.CHECKCAST, type.elementType.internalName)
            )
          } else if (insnNode.opcode == Opcodes.BALOAD) {
            val methodInsnNode = MethodInsnNode(
              Opcodes.INVOKESTATIC,
              COLLECTION_DEFENSOR.toInternalName(),
              "get",
              "(${arrayDescriptor}I)${type.elementType.descriptor}",
              false
            )
            methodNode.instructions.set(insnNode, methodInsnNode)
          }
        }
      }
    }
  }
}

