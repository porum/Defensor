package com.panda912.defensor.plugin.extension

/**
 * Created by panda on 2021/9/23 10:52
 */
open class DefaultDefensorExtension : DefensorExtension {
  override var enable: Boolean = true
  override var excludes: List<String> = listOf()
}