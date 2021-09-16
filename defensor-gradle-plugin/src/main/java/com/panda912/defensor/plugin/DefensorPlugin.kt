package com.panda912.defensor.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.BadPluginException
import com.panda912.defensor.plugin.transform.AppDefensorTransform
import com.panda912.defensor.plugin.transform.LibDefensorTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by panda on 2021/8/17 14:04
 */
class DefensorPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    val extension = target.extensions.getByType(BaseExtension::class.java)
    val transform = when (extension) {
      is AppExtension -> AppDefensorTransform()
      is LibraryExtension -> LibDefensorTransform()
      else -> throw BadPluginException("Required android application or library module.")
    }
    extension.registerTransform(transform)
  }
}