package com.panda912.defensor.plugin.internal

/**
 * Created by panda on 2021/9/14 15:43
 */
class BytecodeInterceptorChain(
  private val interceptors: List<BytecodeInterceptor>,
  private val index: Int,
  private val input: Input
) : BytecodeInterceptor.Chain {

  override fun request(): Input = input

  override fun proceed(input: Input): Output {
    check(index < interceptors.size)
    val interceptor = interceptors[index]
    val next = BytecodeInterceptorChain(interceptors, index + 1, input)
    return interceptor.intercept(next)
  }
}