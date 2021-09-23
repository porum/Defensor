package com.panda912.defensor.plugin.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.internal.pipeline.TransformManager
import com.panda912.defensor.plugin.Global
import com.panda912.defensor.plugin.extension.DefaultDefensorExtension

/**
 * Created by panda on 2021/8/17 14:15
 */
class LibDefensorTransform(
  global: Global,
  extension: DefaultDefensorExtension
) : DefensorTransform(global, extension) {
  override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
    return TransformManager.PROJECT_ONLY
  }
}