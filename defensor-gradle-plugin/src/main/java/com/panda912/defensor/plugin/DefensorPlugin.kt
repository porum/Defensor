package com.panda912.defensor.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.BadPluginException
import com.panda912.defensor.plugin.extension.DefaultDefensorExtension
import com.panda912.defensor.plugin.extension.DefensorExtension
import com.panda912.defensor.plugin.transform.AppDefensorTransform
import com.panda912.defensor.plugin.transform.LibDefensorTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by panda on 2021/8/17 14:04
 */
class DefensorPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    val ext = target.extensions.create(
      DefensorExtension::class.java,
      "defensor",
      DefaultDefensorExtension::class.java
    ) as DefaultDefensorExtension

    val extension = target.extensions.getByType(BaseExtension::class.java)
    val global = Global(target, extension, AndroidJarProvider.DEFAULT)
    val transform = when (extension) {
      is AppExtension -> AppDefensorTransform(global, ext)
      is LibraryExtension -> LibDefensorTransform(global, ext)
      else -> throw BadPluginException("Required android application or library module.")
    }
    extension.registerTransform(transform)
  }
}