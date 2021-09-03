package com.panda912.defensor.plugin.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent.ContentType
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.panda912.defensor.plugin.bytecode.DefensorClassVisitor
import com.panda912.defensor.plugin.utils.DOT_CLASS
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

    val isIncremental = transformInvocation.isIncremental && isIncremental
    val dirOrJarFiles = mutableListOf<Pair<File, File>>()
    val changedFiles = hashMapOf<File, Status>()

    transformInvocation.inputs.forEach { transformInput ->

      transformInput.directoryInputs.forEach { dirInput ->
        val inputFile = dirInput.file
        val outputFile = transformInvocation.outputProvider.getContentLocation(
          dirInput.name,
          dirInput.contentTypes,
          dirInput.scopes,
          Format.DIRECTORY
        )

        dirOrJarFiles.add(Pair(inputFile, outputFile))
        changedFiles.putAll(dirInput.changedFiles)
      }

      transformInput.jarInputs.forEach { jarInput ->
        val inputJar = jarInput.file
        val outputJar = transformInvocation.outputProvider.getContentLocation(
          jarInput.name,
          jarInput.contentTypes,
          jarInput.scopes,
          Format.JAR
        )

        dirOrJarFiles.add(Pair(inputJar, outputJar))
        changedFiles[inputJar] = jarInput.status
      }
    }

    doTransform(isIncremental, dirOrJarFiles, changedFiles)
  }

  private fun doTransform(
    isIncremental: Boolean,
    dirOrJarFiles: List<Pair<File, File>>,
    changedFiles: Map<File, Status>
  ) {

    dirOrJarFiles.forEach { pair ->
      val input = pair.first
      val output = pair.second

      if (input.isDirectory) {
        if (isIncremental) {
          changedFiles.filter { it.key.startsWith(input) }.forEach { (changedFile, status) ->
            val outputFile = FileUtils.getOutputFile(input, changedFile, output)
            when (status) {
              Status.ADDED,
              Status.CHANGED -> modifyByteCode(changedFile, outputFile)
              Status.REMOVED -> FileUtils.deleteIfExists(outputFile)
            }
          }
        } else {
          output.deleteRecursively()
          input.walkTopDown().toList().parallelStream().filter { it.isFile }.forEach {
            val outputFile = FileUtils.getOutputFile(input, it, output)
            if (it.name.endsWith(DOT_CLASS) && !it.isDefensorClass()) {
              FileUtils.ensureParentDirsCreated(outputFile)
              modifyByteCode(it, outputFile)
            } else {
              it.copyTo(outputFile, true)
            }
          }
        }
      } else {
        output.deleteRecursively()
        input.copyRecursively(output, true)
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