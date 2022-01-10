package com.panda912.defensor.plugin.internal.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode

/**
 * ref: https://mlog.club/article/3432039
 *
 * Created by panda on 2022/1/10 10:17
 */
abstract class IntegrateNodeTransformer(
  private val target: ClassVisitor?
) : ClassVisitor(Opcodes.ASM9, null) {

  /**
   * Do the actual work with the Tree API.
   * May manipulate {@code source} directly and return it or return an entirely new tree.
   */
  abstract fun process(source: ClassNode): ClassNode

  /**
   * Initiate the building of a tree when the visit of a class starts.
   */
  override fun visit(
    version: Int,
    access: Int,
    name: String?,
    signature: String?,
    superName: String?,
    interfaces: Array<out String>?
  ) {
    super.cv = ClassNode()
    super.visit(version, access, name, signature, superName, interfaces)
  }

  /**
   * On completion of visiting the source class, process the tree and initiate
   * visiting of the result by the target visitor, if there is one.
   */
  override fun visitEnd() {
    super.visitEnd()
    if (target != null) {
      val result = process(super.cv as ClassNode)
      result.accept(target)
    }
  }

}