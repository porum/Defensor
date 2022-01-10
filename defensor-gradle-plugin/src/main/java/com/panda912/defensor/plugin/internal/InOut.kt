package com.panda912.defensor.plugin.internal

import com.android.build.api.instrumentation.ClassContext
import org.objectweb.asm.ClassVisitor

/**
 * Created by panda on 2021/9/14 16:16
 */
data class In(
  val classContext: ClassContext,
  var classVisitor: ClassVisitor
)

data class Output(
  val classContext: ClassContext,
  val classVisitor: ClassVisitor
)