plugins {
  id("java-library")
  kotlin("jvm")
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.15.0"
  id("org.jetbrains.dokka") version "1.5.0"
}

dependencies {
  implementation(gradleApi())
  compileOnly("com.android.tools.build:gradle:4.2.2")
  compileOnly(kotlin("gradle-plugin", "1.5.21"))
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

