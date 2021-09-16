package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.TransformContext
import java.io.File

/**
 * Created by panda on 2021/9/14 17:21
 */
interface QualifiedContentProcessor {
  fun process(context: TransformContext)
}

interface SpecifiedQualifiedContentProcessor : QualifiedContentProcessor {
  fun doTransform(inputFile: File, outputFile: File)
}