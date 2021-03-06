package com.panda912.defensor.plugin.internal.interceptor

import android.app.Fragment
import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 17:39
 */
class FragmentInterceptor : BytecodeInterceptor {

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
        return FragmentMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class FragmentMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String?,
    name: String?,
    descriptor: String?,
    isInterface: Boolean
  ) {

    if (opcode == Opcodes.INVOKEVIRTUAL) {

      // Fragment
      if (
        (name == "getContext" && descriptor == "()Landroid/content/Context;") ||
        (name == "getActivity" && descriptor == "()Landroid/app/Activity;") ||
        (name == "isAdded" && descriptor == "()Z") ||
        (name == "isDetached" && descriptor == "()Z") ||
        (name == "isRemoving" && descriptor == "()Z") ||
        (name == "isInLayout" && descriptor == "()Z") ||
        (name == "isResumed" && descriptor == "()Z") ||
        (name == "isVisible" && descriptor == "()Z") ||
        (name == "isHidden" && descriptor == "()Z") ||
        (name == "startActivity" && (descriptor == "(Landroid/content/Intent;)V" || descriptor == "(Landroid/content/Intent;Landroid/os/Bundle;)V")) ||
        (name == "startActivityForResult" && (descriptor == "(Landroid/content/Intent;I)V" || descriptor == "(Landroid/content/Intent;ILandroid/os/Bundle;)V")) ||
        (name == "getView" && descriptor == "()Landroid/view/View;")
      ) {
        if (owner == Type.getInternalName(Fragment::class.java)) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENT_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(Fragment::class.java)),
            isInterface
          )
          return
        } else if (owner == Type.getInternalName(androidx.fragment.app.Fragment::class.java)) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENTX_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(androidx.fragment.app.Fragment::class.java)),
            isInterface
          )
          return
        }
      }

      if (owner == FRAGMENT_MANAGER_CLASS.toInternalName()) {
        if (
          (name == "popBackStack" && descriptor == "()V") ||
          (name == "putFragment" && descriptor == "(Landroid/os/Bundle;Ljava/lang/String;Landroidx/fragment/app/Fragment;)V") ||
          (name == "getFragment" && descriptor == "(Landroid/os/Bundle;Ljava/lang/String;)Landroidx/fragment/app/Fragment;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FRAGMENT_MANAGER_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(Type.getDescriptor(androidx.fragment.app.FragmentManager::class.java)),
            isInterface
          )
          return
        }
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}