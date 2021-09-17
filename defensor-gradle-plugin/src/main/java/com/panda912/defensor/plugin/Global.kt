package com.panda912.defensor.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import java.io.File

/**
 * Created by panda on 2021/9/10 16:26
 */
class Global(
  private val project: Project,
  private val android: BaseExtension,
  private val androidJarProvider: AndroidJarProvider,
) {

  fun getProject(): Project = project

  fun getAndroid(): BaseExtension = android

  fun getAndroidJar(): File = androidJarProvider.getAndroidJar(android)

}