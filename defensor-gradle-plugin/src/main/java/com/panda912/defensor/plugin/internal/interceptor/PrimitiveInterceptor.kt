package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.AbstractInsnNode.METHOD_INSN
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * Created by panda on 2021/9/15 11:20
 */
class PrimitiveInterceptor : BytecodeInterceptor {

  override fun intercept(chain: BytecodeInterceptor.Chain): Output {
    val input = chain.request()

    val cr = BaseClassReader(input.bytes)
    val classNode = ClassNode()
    cr.accept(classNode, ClassReader.EXPAND_FRAMES)

    for (methodNode in classNode.methods) {
      for (insnNode in methodNode.instructions.toArray()) {
        if (insnNode.type == METHOD_INSN && insnNode.opcode == Opcodes.INVOKESTATIC) {
          val methodInsn = insnNode as MethodInsnNode
          if (
            (methodInsn.owner == BYTE_CLASS.toInternalName() && methodInsn.name == "parseByte" && methodInsn.desc == "(Ljava/lang/String;)B") ||
            (methodInsn.owner == SHORT_CLASS.toInternalName() && methodInsn.name == "parseShort" && methodInsn.desc == "(Ljava/lang/String;)S") ||
            (methodInsn.owner == INTEGER_CLASS.toInternalName() && methodInsn.name == "parseInt" && methodInsn.desc == "(Ljava/lang/String;)I") ||
            (methodInsn.owner == LONG_CLASS.toInternalName() && methodInsn.name == "parseLong" && methodInsn.desc == "(Ljava/lang/String;)J") ||
            (methodInsn.owner == FLOAT_CLASS.toInternalName() && methodInsn.name == "parseFloat") ||
            (methodInsn.owner == DOUBLE_CLASS.toInternalName() && methodInsn.name == "parseDouble") ||
            (methodInsn.owner == BOOLEAN_CLASS.toInternalName() && methodInsn.name == "parseBoolean")
          ) {
            val index = methodNode.instructions.indexOf(insnNode)
            val hasCatched = methodNode.tryCatchBlocks.any {
              val start = methodNode.instructions.indexOf(it.start)
              val end = methodNode.instructions.indexOf(it.end)
              val inTryBlock = index in start until end
              val isCatching = it.type == Type.getInternalName(NumberFormatException::class.java) ||
                  it.type == Type.getInternalName(IllegalArgumentException::class.java) ||
                  it.type == Type.getInternalName(RuntimeException::class.java) ||
                  it.type == Type.getInternalName(Exception::class.java) ||
                  it.type == Type.getInternalName(Throwable::class.java)
              inTryBlock && isCatching
            }

            val newInsnNode = MethodInsnNode(
              Opcodes.INVOKESTATIC,
              PRIMITIVE_TYPE_PARSER.toInternalName(),
              if (hasCatched) "${insnNode.name}Throw" else insnNode.name,
              insnNode.desc,
              false
            )
            methodNode.instructions.set(insnNode, newInsnNode)
          }
        }
      }
    }

    val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
    classNode.accept(cw)
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}