package com.panda912.defensor.plugin.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.internal.pipeline.TransformManager

/**
 * Created by panda on 2021/8/17 14:15
 */
class LibDefensorTransform : DefensorTransform() {
  override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
    return TransformManager.PROJECT_ONLY
  }
}