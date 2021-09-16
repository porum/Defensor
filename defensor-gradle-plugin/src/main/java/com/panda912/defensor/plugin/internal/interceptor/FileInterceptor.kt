package com.panda912.defensor.plugin.internal.interceptor

import com.panda912.defensor.plugin.internal.BytecodeInterceptor
import com.panda912.defensor.plugin.internal.Output
import com.panda912.defensor.plugin.internal.visitor.BaseClassReader
import com.panda912.defensor.plugin.internal.visitor.BaseClassVisitor
import com.panda912.defensor.plugin.internal.visitor.BaseMethodVisitor
import com.panda912.defensor.plugin.utils.FILE_DEFENSOR
import com.panda912.defensor.plugin.utils.SAFE_FILE
import com.panda912.defensor.plugin.utils.convertToStaticDescriptor
import com.panda912.defensor.plugin.utils.toInternalName
import org.objectweb.asm.*
import java.io.File

/**
 * Created by panda on 2021/9/14 16:29
 */
class FileInterceptor : BytecodeInterceptor {

  override fun intercept(chain: BytecodeInterceptor.Chain): Output {
    val input = chain.request()

    val cr = BaseClassReader(input.bytes)
    val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
    cr.accept(object : BaseClassVisitor(cw) {
      override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
      ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return FileMethodVisitor(mv)
      }
    })
    input.bytes = cw.toByteArray()
    return chain.proceed(input)
  }
}

class FileMethodVisitor(mv: MethodVisitor) : BaseMethodVisitor(mv) {

  override fun visitTypeInsn(opcode: Int, type: String?) {
    if (opcode == Opcodes.NEW && type == Type.getInternalName(File::class.java)) {
      super.visitTypeInsn(opcode, SAFE_FILE.toInternalName())
    } else {
      super.visitTypeInsn(opcode, type)
    }
  }

  override fun visitMethodInsn(
    opcode: Int,
    owner: String,
    name: String,
    descriptor: String,
    isInterface: Boolean
  ) {

    // File
    if (owner == Type.getInternalName(File::class.java)) {
      val fileDescriptor = Type.getDescriptor(File::class.java)
      if (opcode == Opcodes.INVOKESPECIAL && name == "<init>") {
        super.visitMethodInsn(opcode, SAFE_FILE.toInternalName(), name, descriptor, isInterface)
        return
      }

      if (opcode == Opcodes.INVOKEVIRTUAL) {
        if (
          (name == "getName" && descriptor == "()Ljava/lang/String;") ||
          (name == "getParent" && descriptor == "()Ljava/lang/String;") ||
          (name == "getParentFile" && descriptor == "()Ljava/io/File;") ||
          (name == "getPath" && descriptor == "()Ljava/lang/String;") ||
          (name == "isAbsolute" && descriptor == "()Z") ||
          (name == "getAbsolutePath" && descriptor == "()Ljava/lang/String;") ||
          (name == "getAbsoluteFile" && descriptor == "()Ljava/io/File;") ||
          (name == "getCanonicalPath" && descriptor == "()Ljava/lang/String;") ||
          (name == "getCanonicalFile" && descriptor == "()Ljava/io/File;") ||
          (name == "toURL" && descriptor == "()Ljava/net/URL;") ||
          (name == "toURI" && descriptor == "()Ljava/net/URI;") ||
          (name == "canRead" && descriptor == "()Z") ||
          (name == "canWrite" && descriptor == "()Z") ||
          (name == "exists" && descriptor == "()Z") ||
          (name == "isDirectory" && descriptor == "()Z") ||
          (name == "isFile" && descriptor == "()Z") ||
          (name == "isHidden" && descriptor == "()Z") ||
          (name == "lastModified" && descriptor == "()J") ||
          (name == "length" && descriptor == "()J") ||
          (name == "createNewFile" && descriptor == "()Z") ||
          (name == "delete" && descriptor == "()Z") ||
          (name == "deleteOnExit" && descriptor == "()V") ||
          (name == "list" && descriptor == "()[Ljava/lang/String;") ||
          (name == "list" && descriptor == "(Ljava/io/FilenameFilter;)[Ljava/lang/String;") ||
          (name == "listFiles" && descriptor == "()[Ljava/io/File;") ||
          (name == "listFiles" && descriptor == "(Ljava/io/FilenameFilter;)[Ljava/io/File;") ||
          (name == "listFiles" && descriptor == "(Ljava/io/FileFilter;)[Ljava/io/File;") ||
          (name == "mkdir" && descriptor == "()Z") ||
          (name == "mkdirs" && descriptor == "()Z") ||
          (name == "renameTo" && descriptor == "(Ljava/io/File;)Z") ||
          (name == "setLastModified" && descriptor == "(J)Z") ||
          (name == "setReadOnly" && descriptor == "()Z") ||
          (name == "setWritable" && descriptor == "(ZZ)Z") ||
          (name == "setWritable" && descriptor == "(Z)Z") ||
          (name == "setReadable" && descriptor == "(ZZ)Z") ||
          (name == "setReadable" && descriptor == "(Z)Z") ||
          (name == "setExecutable" && descriptor == "(ZZ)Z") ||
          (name == "setExecutable" && descriptor == "(Z)Z") ||
          (name == "canExecute" && descriptor == "()Z") ||
          (name == "getTotalSpace" && descriptor == "()J") ||
          (name == "getFreeSpace" && descriptor == "()J") ||
          (name == "getUsableSpace" && descriptor == "()J") ||
          (name == "toString" && descriptor == "()Ljava/lang/String;") ||
          (name == "toPath" && descriptor == "()Ljava/nio/file/Path;")
        ) {
          super.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            FILE_DEFENSOR.toInternalName(),
            name,
            descriptor.convertToStaticDescriptor(fileDescriptor),
            isInterface
          )
          return
        }
      }
    }

    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
  }
}