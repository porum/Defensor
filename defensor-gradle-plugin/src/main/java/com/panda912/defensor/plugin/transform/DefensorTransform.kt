package com.panda912.defensor.plugin.transform

import com.android.build.api.transform.QualifiedContent.ContentType
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.bytecode.DefensorClassVisitor
import com.panda912.defensor.plugin.utils.FileUtils
import com.panda912.defensor.plugin.utils.isDefensorClass
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileInputStream

/**
 * Created by panda on 2021/8/17 14:05
 */
abstract class DefensorTransform : Transform() {
  override fun getName(): String = "defensor"

  override fun getInputTypes(): MutableSet<ContentType> = TransformManager.CONTENT_CLASS

  override fun isIncremental(): Boolean = true

  override fun transform(transformInvocation: TransformInvocation) {
    super.transform(transformInvocation)

    val transformContext = TransformContext(transformInvocation)
    doTransform(transformContext)
  }

  private fun doTransform(transformContext: TransformContext) {
    with(transformContext) {
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
                Status.ADDED,
                Status.CHANGED -> modifyByteCode(changedFile, outputFile)
                Status.REMOVED -> FileUtils.deleteIfExists(outputFile)
              }
            }
        } else {
          getOutputFile(directoryInput).deleteRecursively()
          inputDir.walkTopDown().toList().parallelStream().filter { it.isFile }.forEach {
            val outputFile = FileUtils.getOutputFile(inputDir, it, outputDir)
            if (it.extension == "class" && !it.isDefensorClass()) {
              FileUtils.ensureParentDirsCreated(outputFile)
              modifyByteCode(it, outputFile)
            } else {
              it.copyTo(outputFile, true)
            }
          }
        }
      }
      getAllJars().forEach { jarInput ->
        val inputJar = jarInput.file
        val outputJar = getOutputFile(jarInput)
        outputJar.deleteRecursively()
        inputJar.copyRecursively(outputJar, true)
      }
    }
  }

  private fun modifyByteCode(classFile: File, destFile: File) {
    val classReader = ClassReader(FileInputStream(classFile))
    val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
    classReader.accept(DefensorClassVisitor(classWriter), Opcodes.ASM7)
    destFile.writeBytes(classWriter.toByteArray())
  }
}