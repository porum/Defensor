package com.panda912.defensor.plugin.internal.processor

import com.android.build.api.transform.Status
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.utils.FileUtils
import com.panda912.defensor.plugin.utils.isDefensorClass
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * Created by panda on 2021/9/14 17:47
 */
class JarProcessor : QualifiedContentProcessor {

  override fun process(context: TransformContext, injector: BytecodeInjector) {
    with(context) {
      getAllJars().forEach { jarInput ->
        val inputJar = jarInput.file
        val outputJar = getOutputFile(jarInput)
        if (isIncremental()) {
          when (getChangedFiles()[jarInput.file]) {
            Status.REMOVED -> FileUtils.deleteIfExists(outputJar)
            Status.ADDED,
            Status.CHANGED -> {
              FileUtils.deleteIfExists(outputJar)
              unzip(context, injector, inputJar, outputJar)
            }
          }
        } else {
          FileUtils.deleteIfExists(outputJar)
          unzip(context, injector, inputJar, outputJar)
        }
      }
    }
  }

  private fun unzip(
    context: TransformContext,
    injector: BytecodeInjector,
    inputFile: File,
    outputFile: File
  ) {
    val jarFile = JarFile(inputFile)
    val jos = JarOutputStream(outputFile.outputStream().buffered())
    jarFile.entries().iterator().forEachRemaining { entry ->
      if (!entry.isDirectory) {
        jos.putNextEntry(JarEntry(entry.name))
        val bytes = jarFile.getInputStream(entry).readBytes()
        if (entry.name.endsWith(".class") && !entry.name.isDefensorClass()) {
          with(injector.preCheck(context, bytes)) {
            jos.write(if (ignored) bytes else injector.inject(entry.name, className, bytes))
          }
        } else {
          jos.write(bytes)
        }
      }
    }
    jos.finish()
    jos.flush()
    jos.close()
  }

}