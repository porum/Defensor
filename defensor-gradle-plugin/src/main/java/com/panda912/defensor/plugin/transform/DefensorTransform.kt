package com.panda912.defensor.plugin.transform

import com.android.build.api.transform.QualifiedContent.ContentType
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.panda912.defensor.plugin.Global
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.internal.processor.ClassifiedContentProcessor
import com.panda912.defensor.plugin.internal.processor.DirectoryProcessor
import com.panda912.defensor.plugin.internal.processor.JarProcessor

/**
 * Created by panda on 2021/8/17 14:05
 */
abstract class DefensorTransform(private val global: Global) : Transform() {
  override fun getName(): String = "defensor"

  override fun getInputTypes(): MutableSet<ContentType> = TransformManager.CONTENT_CLASS

  override fun isIncremental(): Boolean = true

  override fun transform(transformInvocation: TransformInvocation) {
    super.transform(transformInvocation)
    val transformContext = TransformContext(global, transformInvocation)
    val qualifiedContentProcessor = ClassifiedContentProcessor.newInstance(
      DirectoryProcessor(),
      JarProcessor()
    )
    qualifiedContentProcessor.process(transformContext)
  }
}