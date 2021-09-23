package com.panda912.defensor.plugin.extension

/**
 * Created by panda on 2021/9/23 10:52
 */
open class DefaultDefensorExtension : DefensorExtension {

  var enable: Boolean = true
  var excludes = listOf<String>()

  override fun enable(enable: Boolean) {
    this.enable = enable
  }

  override fun excludes(list: List<String>) {
    this.excludes = list
  }
}