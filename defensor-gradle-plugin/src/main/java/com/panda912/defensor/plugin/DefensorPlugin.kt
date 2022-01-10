package com.panda912.defensor.plugin

import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.LibraryVariant
import com.android.build.gradle.internal.BadPluginException
import com.panda912.defensor.plugin.internal.processor.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.create
import org.objectweb.asm.ClassVisitor

/**
 * Created by panda on 2022/1/7 16:15.
 */
@Suppress("UnstableApiUsage")
class DefensorPlugin : Plugin<Project> {

  override fun apply(target: Project) {
    val config = target.extensions.create<DefensorExtension>("defensor")
    val androidExtension = target.extensions.getByType(AndroidComponentsExtension::class.java)
    androidExtension.onVariants { variant ->
      if (!config.enabled || config.includes.isEmpty()) {
        return@onVariants
      }
      val scope = when (variant) {
        is ApplicationVariant -> InstrumentationScope.ALL
        is LibraryVariant -> InstrumentationScope.PROJECT
        else -> throw BadPluginException("Required android application or library module.")
      }
      variant.transformClassesWith(DefensorAsmClassVisitorFactory::class.java, scope) {
        it.includes.set(config.includes)
      }
      variant.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
    }
  }
}

@Suppress("UnstableApiUsage")
abstract class DefensorAsmClassVisitorFactory : AsmClassVisitorFactory<ConfigParams> {

  override fun createClassVisitor(
    classContext: ClassContext,
    nextClassVisitor: ClassVisitor
  ): ClassVisitor {
    return DefensorClassVisitor(classContext, nextClassVisitor)
  }

  override fun isInstrumentable(classData: ClassData): Boolean {
    val includes = parameters.get().includes.get()
    return includes.any { classData.className.startsWith(it) }
  }
}

@Suppress("UnstableApiUsage")
interface ConfigParams : InstrumentationParameters {

  @get:Input
  val includes: ListProperty<String>
}