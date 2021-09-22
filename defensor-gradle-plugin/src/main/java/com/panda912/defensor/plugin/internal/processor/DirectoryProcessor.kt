package com.panda912.defensor.plugin.internal.processor

import com.android.build.api.transform.Status
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.internal.BytecodeInterceptorChain
import com.panda912.defensor.plugin.internal.Input
import com.panda912.defensor.plugin.internal.interceptor.*
import com.panda912.defensor.plugin.utils.FileUtils
import com.panda912.defensor.plugin.utils.isDefensorClass
import java.io.File

/**
 * Created by panda on 2021/9/14 17:47
 */
class DirectoryProcessor : SpecifiedQualifiedContentProcessor {

  override fun process(context: TransformContext) {
    with(context) {
      getAllDirs().forEach { directoryInput ->
        val inputDir = directoryInput.file
        val outputDir = getOutputFile(directoryInput)
        if (isIncremental()) {
          getChangedFiles()
            .filter {
              it.key.startsWith(inputDir) && it.key.extension == "class"
            }
            .forEach { (changedFile, status) ->
              val outputFile = FileUtils.getOutputFile(inputDir, changedFile, outputDir)
              when (status) {
                Status.REMOVED -> FileUtils.deleteIfExists(outputFile)
                Status.ADDED,
                Status.CHANGED -> {
                  doTransform(changedFile, outputFile)
                }
              }
            }
        } else {
          outputDir.deleteRecursively()
          inputDir.walkTopDown().toList().parallelStream().filter { it.isFile }.forEach {
            val outputFile = FileUtils.getOutputFile(inputDir, it, outputDir)
            if (it.extension == "class" && !it.invariantSeparatorsPath.isDefensorClass()) {
              FileUtils.ensureParentDirsCreated(outputFile)
              doTransform(it, outputFile)
            } else {
              it.copyTo(outputFile, true)
            }
          }
        }
      }
    }
  }

  override fun doTransform(inputFile: File, outputFile: File) {
    val interceptors = listOf(
      UnboxingInterceptor(),
      PrimitiveInterceptor(),
      CollectionInterceptor(),
      StringInterceptor(),
      FileInterceptor(),
      JsonInterceptor(),
      ThrowableInterceptor(),
      ActivityInterceptor(),
      FragmentInterceptor(),
      SafeDialogInterceptor(),
      IntentInterceptor(),
      ViewInterceptor(),
      DeadObjectInterceptor(),
      ContextInterceptor(),
      UriInterceptor(),
      LiveDataInterceptor(),
      PaintInterceptor(),
      GridLayoutManagerInterceptor(),
      ValueAnimatorInterceptor()
    )
    val input = Input(inputFile.absolutePath, inputFile.readBytes())
    val chain = BytecodeInterceptorChain(interceptors + FinalInterceptor(), 0, input)
    val output = chain.proceed(input)
    outputFile.writeBytes(output.bytes)
  }

}