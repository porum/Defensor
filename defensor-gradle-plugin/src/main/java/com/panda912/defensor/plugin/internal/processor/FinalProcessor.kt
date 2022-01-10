package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.internal.BytecodeProcessor
import com.panda912.defensor.plugin.internal.Output

/**
 * Created by panda on 2021/9/15 16:50
 */
class FinalProcessor : BytecodeProcessor {

  override fun process(chain: BytecodeProcessor.Chain): Output {
    val input = chain.input()
    return Output(input.classContext, input.classVisitor)
  }

}