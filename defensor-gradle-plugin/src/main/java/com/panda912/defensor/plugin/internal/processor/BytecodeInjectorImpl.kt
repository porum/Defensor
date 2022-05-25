package com.panda912.defensor.plugin.internal.processor

import com.panda912.defensor.plugin.TransformContext
import com.panda912.defensor.plugin.internal.BytecodeInterceptorChain
import com.panda912.defensor.plugin.internal.Input
import com.panda912.defensor.plugin.internal.interceptor.*
import org.objectweb.asm.ClassReader

/**
 * Created by panda on 2021/9/23 13:54
 */
class BytecodeInjectorImpl : BytecodeInjector {

  override fun preCheck(context: TransformContext, bytes: ByteArray): PreCheckResult {
    val cr = ClassReader(bytes)
    val className = cr.className
    val ignored = !context.isEnable() || context.excludes().any { className.startsWith(it) }
    return PreCheckResult(className, ignored)
  }

  override fun inject(fullName: String, className: String, bytes: ByteArray): ByteArray {
    val interceptors = listOf(
      ActivityInterceptor(),
      ArrayInterceptor(),
      CollectionInterceptor(),
      ContextInterceptor(),
      DeadObjectInterceptor(),
      EditableInterceptor(),
      FileInterceptor(),
      FragmentInterceptor(),
      GridLayoutManagerInterceptor(),
      HandlerInterceptor(),
      IntentInterceptor(),
      JsonInterceptor(),
      LiveDataInterceptor(),
      PaintInterceptor(),
      PrimitiveInterceptor(),
      SafeDialogInterceptor(),
      StringInterceptor(),
      ThrowableInterceptor(),
      ToastInterceptor(),
      UnboxingInterceptor(),
      UriInterceptor(),
      ValueAnimatorInterceptor(),
      ViewInterceptor(),
    )
    val input = Input(fullName, className, bytes)
    val chain = BytecodeInterceptorChain(interceptors + FinalInterceptor(), 0, input)
    val output = chain.proceed(input)
    return output.bytes
  }
}