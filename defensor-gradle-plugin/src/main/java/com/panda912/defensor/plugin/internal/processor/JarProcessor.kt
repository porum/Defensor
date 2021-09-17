package com.panda912.defensor.plugin.internal.processor

import com.android.build.api.transform.Status
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.internal.BytecodeInterceptorChain
import com.panda912.defensor.plugin.internal.Input
import com.panda912.defensor.plugin.internal.interceptor.*
import com.panda912.defensor.plugin.utils.FileUtils
import com.panda912.defensor.plugin.utils.isDefensorClass
import java.io.File
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * Created by panda on 2021/9/14 17:47
 */
class JarProcessor : SpecifiedQualifiedContentProcessor {

  private lateinit var transformContext: TransformContext

  override fun process(context: TransformContext) {
    transformContext = context
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
              doTransform(inputJar, outputJar)
            }
          }
        } else {
          FileUtils.deleteIfExists(outputJar)
          doTransform(inputJar, outputJar)
        }
      }
    }
  }

  override fun doTransform(inputFile: File, outputFile: File) {
    val jarFile = JarFile(inputFile)
    val jos = JarOutputStream(outputFile.outputStream().buffered())
    jarFile.entries().iterator().forEachRemaining { entry ->
      if (!entry.isDirectory) {
        val inputStream = jarFile.getInputStream(entry)
        val bytes = if (entry.name.endsWith(".class") && !entry.name.isDefensorClass()) {
          getTransformedJarClass(entry.name, inputStream)
        } else {
          inputStream.readBytes()
        }
        jos.putNextEntry(JarEntry(entry.name))
        jos.write(bytes)
      }
    }
    jos.finish()
    jos.flush()
    jos.close()
  }

  private fun getTransformedJarClass(name: String, inputStream: InputStream): ByteArray {
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
    )
    val input = Input(name, inputStream.readBytes())
    val chain = BytecodeInterceptorChain(interceptors + FinalInterceptor(), 0, input)
    val output = chain.proceed(input)
    return output.bytes
  }

}