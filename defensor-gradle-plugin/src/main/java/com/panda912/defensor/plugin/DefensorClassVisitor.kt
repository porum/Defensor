package com.panda912.defensor.plugin

import com.android.build.api.instrumentation.ClassContext
import com.panda912.defensor.plugin.internal.BytecodeProcessorChain
import com.panda912.defensor.plugin.internal.In
import com.panda912.defensor.plugin.internal.processor.*
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

/**
 * Created by panda on 2022/1/7 16:53
 */
@Suppress("UnstableApiUsage")
class DefensorClassVisitor(
  private val classContext: ClassContext,
  private val nextClassVisitor: ClassVisitor
) : ClassNode(Opcodes.ASM9) {

  override fun visitEnd() {
    super.visitEnd()

    val processors = listOf(
      ActivityProcessor(),
      ArrayProcessor(),
      CollectionProcessor(),
      ContextProcessor(),
      DeadObjectProcessor(),
      FileProcessor(),
      FragmentProcessor(),
      GridLayoutManagerProcessor(),
      HandlerProcessor(),
      IntentProcessor(),
      JsonProcessor(),
      LiveDataProcessor(),
      PaintProcessor(),
      PrimitiveProcessor(),
      SafeDialogProcessor(),
      StringProcessor(),
      ThrowableProcessor(),
      UnboxingProcessor(),
      UriProcessor(),
      ValueAnimatorProcessor(),
      ViewProcessor(),
    )
    val input = In(classContext, nextClassVisitor)
    val chain = BytecodeProcessorChain(processors + FinalProcessor(), 0, input)
    val output = chain.proceed(input)

    accept(output.classVisitor)
  }

}