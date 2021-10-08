ext {
  this["compileVersion"] = 29
  this["minVersion"] = 21
  this["targetVersion"] = 29
  this["buildTools"] = "29.0.1"
}

val commonConfigure = project.file("common.gradle.kts")
val publishConfigure = project.file("publishing.gradle.kts")

subprojects {
  repositories {
    google()
    mavenLocal()
    maven {
      setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
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