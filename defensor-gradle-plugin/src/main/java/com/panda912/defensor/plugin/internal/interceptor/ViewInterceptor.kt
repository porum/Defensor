package com.panda912.defensor.plugin.internal.interceptor

import android.view.View
import android.widget.TextView
import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.*
import org.objectweb.asm.*

/**
 * Created by panda on 2021/9/15 17:59
 */
class ViewInterceptor : BytecodeInterceptor {

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
        return ViewMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class ViewMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    if (opcode == Opcodes.INVOKESTATIC) {
      if (owner == Type.getInternalName(View::class.java)) {
        if (name == "inflate" && descriptor == "(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;") {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            VIEW_DEFENSOR.toInternalName(),
            name,
            descriptor,
            isInterface
          )
          return
        }
      }
    }

    if (opcode == Opcodes.INVOKEVIRTUAL) {

      // View#setVisibility
      if (name == "setVisibility") {
        try {
          if (View::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              VIEW_DEFENSOR.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor(Type.getDescriptor(View::class.java)),
              isInterface
            )
            return
          }
        } catch (ignored: ClassNotFoundException) {
        }
      }

      // WebView
      if (owner == WEBVIEW_CLASS.toInternalName()) {
        if (
          (name == "loadUrl" && descriptor == "(Ljava/lang/String;)V") ||
          (name == "loadData" && descriptor == "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            VIEW_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroid/webkit/WebView;"),
            isInterface
          )
          return
        }
      }

      // RecyclerView
      if (owner == RECYCLER_CLASS.toInternalName()) {
        if (
          (name == "bindViewToPosition" && descriptor == "(Landroid/view/View;I)V") ||
          (name == "getViewForPosition" && descriptor == "(I)Landroid/view/View;") ||
          (name == "convertPreLayoutPositionToPostLayout" && descriptor == "(I)I")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            VIEW_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroidx/recyclerview/widget/RecyclerView\$Recycler;"),
            isInterface
          )
          return
        }
      }

      // TextView#setText
      if (
        (name == "setText" && (descriptor == "(Ljava/lang/CharSequence;)V" || descriptor == "(I)V" || descriptor == "(ILandroid/widget/TextView\$BufferType;)V" || descriptor == "([CII)V")) ||
        (name == "setHint" && (descriptor == "(Ljava/lang/CharSequence;)V" || descriptor == "(I)V"))
      ) {
        try {
          if (TextView::class.java.isAssignableFrom(Class.forName(owner.toClassName()))) {
            super.visitMethodInsn(
              Opcodes.INVOKESTATIC,
              TEXTVIEW_DEFENSOR.toInternalName(),
              name,
              descriptor.convertToStaticDescriptor(Type.getDescriptor(TextView::class.java)),
              isInterface
            )
            return
          }
        } catch (ignored: ClassNotFoundException) {
        }
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}