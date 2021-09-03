plugins {
  `kotlin-dsl`
}

repositories {
  google()
  mavenLocal()
  mavenCentral()
}

configurations.configureEach {
  resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

val KOTLIN_VERSION: String by project
val AGP_VERSION: String by project
val DEFENSOR_VERSION: String by project

dependencies {
  implementation(gradleApi())
  implementation("com.android.tools.build:gradle:$AGP_VERSION")
  implementation(kotlin("gradle-plugin", KOTLIN_VERSION))
  implementation("io.github.porum:defensor-gradle-plugin:$DEFENSOR_VERSION")
}