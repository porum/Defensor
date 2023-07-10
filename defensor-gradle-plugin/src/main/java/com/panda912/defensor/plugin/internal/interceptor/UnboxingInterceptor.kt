package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.utils.UNBOXING_DEFENSOR
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toClassName
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.*
import org.objectweb.asm.tree.AbstractInsnNode.METHOD_INSN
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * Created by panda on 2021/9/15 11:19
 */
class UnboxingInterceptor : BytecodeInterceptor {

  override fun intercept(chain: BytecodeInterceptor.Chain): Output {
    val input = chain.request()

    val cr = BaseClassReader(input.bytes)
    val classNode = ClassNode()
    cr.accept(classNode, ClassReader.EXPAND_FRAMES)

    for (methodNode in classNode.methods) {
      for (insnNode in methodNode.instructions.toArray()) {
        if (insnNode.type == METHOD_INSN && insnNode.opcode == Opcodes.INVOKEVIRTUAL) {
          val methodInsn = insnNode as MethodInsnNode
          val owner = methodInsn.owner
          val name = methodInsn.name
          val descriptor = methodInsn.desc
          if (
            (owner == Type.getInternalName(java.lang.Short::class.java) && name == "shortValue" && descriptor == "()S") ||
            (owner == Type.getInternalName(java.lang.Integer::class.java) && name == "intValue" && descriptor == "()I") ||
            (owner == Type.getInternalName(java.lang.Long::class.java) && name == "longValue" && descriptor == "()J") ||
            (owner == Type.getInternalName(java.lang.Float::class.java) && name == "floatValue" && descriptor == "()F") ||
            (owner == Type.getInternalName(java.lang.Double::class.java) && name == "doubleValue" && descriptor == "()D") ||
            (owner == Type.getInternalName(java.lang.Byte::class.java) && name == "byteValue" && descriptor == "()B") ||
            (owner == Type.getInternalName(java.lang.Boolean::class.java) && name == "booleanValue" && descriptor == "()Z") ||
            (owner == Type.getInternalName(java.lang.Character::class.java) && name == "charValue" && descriptor == "()C")
          ) {
            val index = methodNode.instructions.indexOf(insnNode)
            val hasCatched = methodNode.tryCatchBlocks.any {
              val start = methodNode.instructions.indexOf(it.start)
              val end = methodNode.instructions.indexOf(it.end)
              val inTryBlock = index in start until end
              val isCatching = it.type == Type.getInternalName(NullPointerException::class.java) ||
                  it.type == Type.getInternalName(RuntimeException::class.java) ||
                  it.type == Type.getInternalName(Exception::class.java) ||
                  it.type == Type.getInternalName(Throwable::class.java)
              inTryBlock && isCatching
            }

            if (!hasCatched) {
              val newInsnNode = MethodInsnNode(
                Opcodes.INVOKESTATIC,
                UNBOXING_DEFENSOR.toInternalName(),
                name,
                descriptor.convertToStaticDescriptor(Type.getDescriptor(Class.forName(owner.toClassName()))),
                false
              )
              methodNode.instructions.set(insnNode, newInsnNode)
            }

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