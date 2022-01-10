package com.panda912.defensor.plugin.internal.processor

import android.view.View
import android.widget.TextView
import com.panda912.defensor.plugin.*
import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Created by panda on 2021/9/15 17:59
 */
class ViewProcessor : BytecodeProcessor {

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
        return ViewMethodVisitor(mv)
      }
    }
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
            RECYCLER_VIEW_DEFENSOR_CLASS.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Landroidx/recyclerview/widget/RecyclerView\$Recycler;"),
            isInterface
          )
          return
        }
      }

      // TextView
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

      // BottomSheetBehavior
      if (owner == BOTTOM_SHEET_BEHAVIOR.toInternalName()) {
        if (
          (name == "setPeekHeight" && (descriptor == "(I)V" || descriptor == "(IZ)V")) ||
          (name == "setState" && descriptor == "(I)V") ||
          (name == "setHalfExpandedRatio" && descriptor == "(F)V") ||
          (name == "setExpandedOffset" && descriptor == "(I)V")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            BOTTOM_SHEET_BEHAVIOR_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor("Lcom/google/android/material/bottomsheet/BottomSheetBehavior;"),
            isInterface
          )
          return
        }
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}