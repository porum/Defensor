plugins {
  // https://github.com/gradle/gradle/issues/20132
  // https://youtrack.jetbrains.com/issue/KT-41142
  `kotlin-dsl`
}

repositories {
  google()
  mavenLocal()
  maven {
    setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
  }
  mavenCentral()
}

val KOTLIN_VERSION: String by project
val AGP_VERSION: String by project
val DEFENSOR_VERSION: String by project

configurations.configureEach {
  resolutionStrategy {
    cacheChangingModulesFor(0, TimeUnit.SECONDS)
//    force("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
  }
}

dependencies {
  implementation(gradleApi())
  implementation("com.android.tools.build:gradle:$AGP_VERSION")
  implementation(kotlin("gradle-plugin", KOTLIN_VERSION))
  // https://github.com/Kotlin/dokka/issues/2472
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:$KOTLIN_VERSION")
  implementation("io.github.porum:defensor-gradle-plugin:$DEFENSOR_VERSION")
}