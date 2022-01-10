package com.panda912.defensor.plugin

/**
 * Created by panda on 2021/9/23 10:50
 */
abstract class DefensorExtension {
  var enabled: Boolean = true
  var includes: List<String> = emptyList()
}