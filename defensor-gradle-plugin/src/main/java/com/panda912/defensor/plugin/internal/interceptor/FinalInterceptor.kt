package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output

/**
 * Created by panda on 2021/9/15 16:50
 */
class FinalInterceptor : BytecodeInterceptor {

  override fun intercept(chain: BytecodeInterceptor.Chain): Output {
    val input = chain.request()
    return Output(input.className, input.bytes)
  }

}