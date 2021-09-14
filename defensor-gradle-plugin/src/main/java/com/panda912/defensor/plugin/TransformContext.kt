package com.panda912.defensor.plugin

import com.android.build.api.transform.*
import java.io.File

/**
 * Created by panda on 2021/9/13 09:24
 */
class TransformContext(private val transformInvocation: TransformInvocation) {
  private val allDirs = arrayListOf<DirectoryInput>()
  private val allJars = arrayListOf<JarInput>()
  private val changedFiles = hashMapOf<File, Status>()

  init {
    transformInvocation.inputs.forEach { transformInput ->
      allDirs.addAll(transformInput.directoryInputs)
      allJars.addAll(transformInput.jarInputs)

      transformInput.directoryInputs.forEach {
        changedFiles.putAll(it.changedFiles)
      }
      transformInput.jarInputs.forEach {
        changedFiles[it.file] = it.status
      }
    }
  }

  fun isIncremental(): Boolean = transformInvocation.isIncremental

  fun getAllDirs(): List<DirectoryInput> = allDirs

  fun getAllJars(): List<JarInput> = allJars

  fun getChangedFiles(): Map<File, Status> = changedFiles

  fun getOutputFile(qualifiedContent: QualifiedContent): File =
    transformInvocation.outputProvider.getContentLocation(
      qualifiedContent.name,
      qualifiedContent.contentTypes,
      qualifiedContent.scopes,
      if (qualifiedContent is JarInput) Format.JAR else Format.DIRECTORY
    )
}