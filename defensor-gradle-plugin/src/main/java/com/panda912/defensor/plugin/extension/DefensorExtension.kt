package com.panda912.defensor.plugin.extension

/**
 * Created by panda on 2021/9/23 10:50
 */
interface DefensorExtension {
  fun enable(enable: Boolean)
  fun excludes(list: List<String>)
}