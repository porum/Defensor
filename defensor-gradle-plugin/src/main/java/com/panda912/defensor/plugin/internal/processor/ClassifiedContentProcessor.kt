package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.TransformContext

/**
 * Created by panda on 2021/9/14 17:53
 */
class ClassifiedContentProcessor private constructor(
  private vararg val processors: QualifiedContentProcessor
) : QualifiedContentProcessor {

  companion object {
    fun newInstance(
      vararg processors: QualifiedContentProcessor
    ): ClassifiedContentProcessor {
      return ClassifiedContentProcessor(*processors)
    }
  }

  override fun process(context: TransformContext) {
    processors.forEach {
      it.process(context)
    }
  }

}