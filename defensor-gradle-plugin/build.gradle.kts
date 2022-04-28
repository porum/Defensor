import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.16.0"
  id("org.jetbrains.dokka")
  `kotlin-dsl`
  `maven-publish`
  signing
}

val AGP_VERSION: String by project
val KOTLIN_VERSION: String by project

dependencies {
  compileOnly("com.android.tools.build:gradle:$AGP_VERSION")
  compileOnly(kotlin("gradle-plugin", KOTLIN_VERSION))
  implementation(gradleApi())
  implementation("org.ow2.asm:asm-tree:9.2")
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

// https://kotlin.github.io/dokka/1.6.0/user_guide/gradle/usage/#configuration-options
tasks.dokkaJavadoc.configure {
  outputDirectory.set(buildDir.resolve("dokka"))
  offlineMode.set(true)
  dokkaSourceSets {
    configureEach {
      // Do not create index pages for empty packages
      skipEmptyPackages.set(true)
      // Disable linking to online kotlin-stdlib documentation
      noStdlibLink.set(false)
      // Disable linking to online Android documentation (only applicable for Android projects)
      noAndroidSdkLink.set(false)

      // Suppress a package
      perPackageOption {
        // will match all .internal packages and sub-packages
        matchingRegex.set(".*\\.internal.*")
        suppress.set(true)
      }
    }
  }
}

val dokkaJavadocJar by tasks.registering(Jar::class) {
  dependsOn(tasks.dokkaJavadoc)
  from(tasks.dokkaJavadoc.get().outputDirectory)
  archiveClassifier.set("javadoc")
}

val sourceJar by tasks.registering(Jar::class) {
  from(sourceSets.main.get().allSource)
  archiveClassifier.set("sources")
}

publishing {
  publications {
    create<MavenPublication>("DefensorGradlePlugin") {
      from(components["java"])
      artifact(sourceJar)
      artifact(dokkaJavadocJar)

      pom {
        name.set("defensor-gradle-plugin")
        description.set("defensor-gradle-plugin")
      }
    }
  }
}

signing {
  sign(extensions.getByType<PublishingExtension>().publications)
}
