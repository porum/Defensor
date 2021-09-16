package com.panda912.defensor.plugin.internal

import java.io.IOException

/**
 * Created by panda on 2021/9/14 15:30
 */
interface BytecodeInterceptor {
  @Throws(IOException::class)
  fun intercept(chain: Chain): Output

  interface Chain {
    fun request(): Input

    @Throws(IOException::class)
    fun proceed(input: Input): Output
  }
}