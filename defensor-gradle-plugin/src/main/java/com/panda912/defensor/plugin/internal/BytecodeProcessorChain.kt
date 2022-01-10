package com.panda912.defensor.plugin.internal

/**
 * Created by panda on 2021/9/14 15:43
 */
class BytecodeProcessorChain(
  private val processors: List<BytecodeProcessor>,
  private val index: Int,
  private val input: In
) : BytecodeProcessor.Chain {

  override fun input(): In = input

  override fun proceed(input: In): Output {
    check(index < processors.size)
    val interceptor = processors[index]
    val next = BytecodeProcessorChain(processors, index + 1, input)
    return interceptor.process(next)
  }
}