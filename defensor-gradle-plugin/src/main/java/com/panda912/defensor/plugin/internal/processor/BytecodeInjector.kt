package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.TransformContext

/**
 * Created by panda on 2021/9/23 13:51
 */
interface BytecodeInjector {
  fun preCheck(context: TransformContext, bytes: ByteArray): PreCheckResult
  fun inject(fullName: String, className: String, bytes: ByteArray): ByteArray
}