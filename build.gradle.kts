ext {
  this["compileVersion"] = 31
  this["minVersion"] = 21
  this["targetVersion"] = 31
  this["buildTools"] = "29.0.1"
}

val publishConfigure = project.file("maven-publish.gradle.kts")

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

  if (name.startsWith("defensor")) {
    apply(from = publishConfigure)
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}