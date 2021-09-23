package com.panda912.defensor.plugin

import com.android.build.api.transform.*
import com.panda912.defensor.plugin.extension.DefaultDefensorExtension
import java.io.File
import java.util.jar.JarFile

/**
 * Created by panda on 2021/9/13 09:24
 */
class TransformContext(
  private val global: Global,
  private val extension: DefaultDefensorExtension,
  private val transformInvocation: TransformInvocation
) {
  private val androidJarClasses = arrayListOf<String>()
  private val allDirs = arrayListOf<DirectoryInput>()
  private val allJars = arrayListOf<JarInput>()
  private val changedFiles = hashMapOf<File, Status>()

  init {
//    unzipAndroidJar()

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

    convertExcludesToInternalName()
  }

  private fun unzipAndroidJar() {
    val androidJar = JarFile(global.getAndroidJar())
    androidJar.entries().iterator().forEachRemaining {
      if (!it.isDirectory) {
        androidJarClasses.add(it.name)
      }
    }
  }

  private fun convertExcludesToInternalName() {
    extension.excludes = extension.excludes.map { it.replace(".", "/") }
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

  fun isAndroidJarClass(className: String): Boolean = androidJarClasses.contains(className)

  fun isEnable(): Boolean = extension.enable

  fun excludes(): List<String> = extension.excludes
}