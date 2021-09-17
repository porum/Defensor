package com.panda912.defensor.plugin

import com.android.build.gradle.BaseExtension
import java.io.File

/**
 * Created by panda on 2021/9/10 16:45
 */
interface AndroidJarProvider {
  fun getAndroidJar(android: BaseExtension): File

  companion object DEFAULT : AndroidJarProvider {
    override fun getAndroidJar(android: BaseExtension): File {
      val sdkPath = arrayOf(
        android.sdkDirectory.absolutePath,
        "platforms",
        android.compileSdkVersion
      ).joinToString(File.separator)
      return File(sdkPath, "android.jar")
    }
  }
}