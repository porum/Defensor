package com.panda912.defensor.plugin.internal.visitor

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import java.io.InputStream

/**
 * Created by panda on 2021/9/16 09:38
 */
open class BaseClassVisitor(cv: ClassVisitor, api: Int = Opcodes.ASM9) : ClassVisitor(api, cv)

open class BaseMethodVisitor(mv: MethodVisitor, api: Int = Opcodes.ASM9) : MethodVisitor(api, mv)

open class BaseClassReader : ClassReader {
  constructor(className: String) : super(className)
  constructor(classFile: ByteArray) : super(classFile)
  constructor(inputStream: InputStream) : super(inputStream)

  fun accept(cv: ClassVisitor) {
    accept(cv, 0)
  }
}

