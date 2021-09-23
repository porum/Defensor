package com.panda912.defensor.plugin.internal.processor

import com.android.build.api.transform.Status
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.utils.FileUtils
import com.panda912.defensor.plugin.utils.isDefensorClass
import java.io.File

/**
 * Created by panda on 2021/9/14 17:47
 */
class DirectoryProcessor : QualifiedContentProcessor {

  override fun process(context: TransformContext, injector: BytecodeInjector) {
    with(context) {
      getAllDirs().forEach { directoryInput ->
        val inputDir = directoryInput.file
        val outputDir = getOutputFile(directoryInput)
        if (isIncremental()) {
          getChangedFiles()
            .filter { it.key.startsWith(inputDir) }
            .forEach { (changedFile, status) ->
              val outputFile = FileUtils.getOutputFile(inputDir, changedFile, outputDir)
              if (status == Status.REMOVED) {
                FileUtils.deleteIfExists(outputFile)
              } else if (status == Status.ADDED || status == Status.CHANGED) {
                doTransform(context, injector, changedFile, outputFile)
              }
            }
        } else {
          outputDir.deleteRecursively()
          inputDir.walkTopDown().toList().parallelStream().filter { it.isFile }.forEach {
            val outputFile = FileUtils.getOutputFile(inputDir, it, outputDir)
            doTransform(context, injector, it, outputFile)
          }
        }
      }
    }
  }

  private fun doTransform(
    context: TransformContext,
    injector: BytecodeInjector,
    inputFile: File,
    outputFile: File
  ) {
    if (inputFile.extension == "class" && !inputFile.invariantSeparatorsPath.isDefensorClass()) {
      val bytes = inputFile.readBytes()
      val preCheckResult = injector.preCheck(context, bytes)
      if (preCheckResult.ignored) {
        inputFile.copyTo(outputFile, true)
      } else {
        FileUtils.ensureParentDirsCreated(outputFile)
        outputFile.writeBytes(
          injector.inject(
            inputFile.absolutePath,
            preCheckResult.className,
            bytes
          )
        )
      }
    } else {
      inputFile.copyTo(outputFile, true)
    }
  }

}