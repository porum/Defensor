package com.panda912.defensor.plugin.internal.processor

import android.app.Activity
import com.panda912.defensor.plugin.ACTIVITY_CLASS
import com.panda912.defensor.plugin.ACTIVITY_DEFENSOR
import com.panda912.defensor.plugin.convertToStaticDescriptor
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.toInternalName
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created by panda on 2021/9/15 17:43
 */
class ActivityProcessor : BytecodeProcessor {
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
        return ActivityMethodVisitor(mv)
      }
    }
    return chain.proceed(input)
  }
}

class ActivityMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (owner == ACTIVITY_CLASS.toInternalName() && opcode == Opcodes.INVOKEVIRTUAL) {
      if (
        (name == "requestPermissions" && descriptor == "([Ljava/lang/String;I)V") ||
        (name == "finish" && descriptor == "()V") ||
        (name == "finishAffinity" && descriptor == "()V") ||
        (name == "finishAfterTransition" && descriptor == "()V") ||
        (name == "finishActivity" && descriptor == "(I)V") ||
        (name == "finishAndRemoveTask" && descriptor == "()V") ||
        (name == "isTaskRoot" && descriptor == "()Z") ||
        (name == "moveTaskToBack" && descriptor == "(Z)Z") ||
        (name == "isFinishing" && descriptor == "()Z") ||
        (name == "isDestroyed" && descriptor == "()Z") ||
        (name == "isChangingConfigurations" && descriptor == "()Z")
      ) {
        super.visitMethodInsn(
          Opcodes.INVOKESTATIC,
          ACTIVITY_DEFENSOR.toInternalName(),
          name,
          descriptor.convertToStaticDescriptor(Type.getDescriptor(Activity::class.java)),
          isInterface
        )
        return
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}