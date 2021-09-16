package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.TransformContext
import java.io.File

/**
 * Created by panda on 2021/9/14 17:47
 */
class JarProcessor : SpecifiedQualifiedContentProcessor {

  override fun process(context: TransformContext) {
    with(context) {
      getAllJars().forEach { jarInput ->
        val inputJar = jarInput.file
        val outputJar = getOutputFile(jarInput)
        outputJar.deleteRecursively()
        inputJar.copyRecursively(outputJar, true)
      }
    }
  }

  override fun doTransform(inputFile: File, outputFile: File) {

  }

}