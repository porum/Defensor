package com.panda912.defensor.plugin.internal

import java.io.IOException

/**
 * Created by panda on 2021/9/14 15:30
 */
interface BytecodeProcessor {
  @Throws(IOException::class)
  fun process(chain: Chain): Output

  interface Chain {
    fun input(): In

    @Throws(IOException::class)
    fun proceed(input: In): Output
  }
}