ext {
  this["compileVersion"] = 29
  this["minVersion"] = 21
  this["targetVersion"] = 29
  this["buildTools"] = "29.0.1"

  this["tests"] = HashMap<String, Any>().apply {
    this["support-runner"] = "com.android.support.test:runner:1.0.2"
  }

  this["appcompat"] = "com.android.support:appcompat-v7:28.0.0"
  this["androidx"] = "androidx.appcompat:appcompat:1.0.2"
  this["gson"] = "com.google.code.gson:gson:2.8.6"
}

val commonConfigure = project.file("common_configure.gradle.kts")
val publishConfigure = project.file("publish_configure.gradle.kts")

subprojects {
  repositories {
    google()
    mavenLocal()
    mavenCentral()
  }

  configurations.configureEach {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
  }

  apply(from = commonConfigure)
  if (name.startsWith("defensor")) {
    apply(from = publishConfigure)
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}