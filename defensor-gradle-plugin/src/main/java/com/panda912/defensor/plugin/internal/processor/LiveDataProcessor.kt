package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.LIVE_DATA_DEFENSOR
import com.panda912.defensor.plugin.MUTABLE_LIVE_DATA_CLASS
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by panda on 2021/9/15 18:23
 */
class LiveDataProcessor : BytecodeProcessor {
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
        return LiveDataMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class LiveDataMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == MUTABLE_LIVE_DATA_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      val newDescriptor = if (
        (name == "observe" && descriptor == "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Observer;)V") ||
        (name == "observeForever" && descriptor == "(Landroidx/lifecycle/Observer;)V")
      ) {
        descriptor.convertToStaticDescriptor("Landroidx/lifecycle/LiveData;")
      } else if (
        (name == "postValue" && descriptor == "(Ljava/lang/Object;)V") ||
        (name == "setValue" && descriptor == "(Ljava/lang/Object;)V")
      ) {
        descriptor.convertToStaticDescriptor("Landroidx/lifecycle/MutableLiveData;")
      } else {
        null
      }

      if (!newDescriptor.isNullOrEmpty()) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          LIVE_DATA_DEFENSOR.toInternalName(),
          name,
          newDescriptor,
          isInterface
        )
        return
      }
    }
    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}