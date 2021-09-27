plugins {
  id("java-library")
  kotlin("jvm")
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.16.0"
  id("org.jetbrains.dokka") version "1.5.30"
}

val AGP_VERSION: String by project
val KOTLIN_VERSION: String by project

dependencies {
  compileOnly("com.android.tools.build:gradle:$AGP_VERSION")
  compileOnly(kotlin("gradle-plugin", KOTLIN_VERSION))
  implementation(gradleApi())
  implementation("org.ow2.asm:asm:9.2")
  implementation(project(":defensor-stub-android"))
}

// https://docs.gradle.org/current/userguide/java_gradle_plugin.html
// https://plugins.gradle.org/docs/publish-plugin
gradlePlugin {
  plugins {
    create("DefensorPlugin") {
      id = "defensor"
      implementationClass = "com.panda912.defensor.plugin.DefensorPlugin"
    }
  }
}

pluginBundle {
  website = "https://github.com/porum/Defensor"
  vcsUrl = "https://github.com/porum/Defensor.git"

  (plugins) {
    "DefensorPlugin" {
      displayName = "Defensor gradle plugin"
      tags = listOf("defensor", "android", "asm")
      description = "Defensor Gradle Plugin for Android"
    }
  }
}

