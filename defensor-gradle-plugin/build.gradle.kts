import com.android.tools.analytics.Environment
import com.panda912.buildsrc.CommonConfig
import org.gradle.jvm.tasks.Jar
import java.util.*

plugins {
  kotlin("jvm")
  `java-gradle-plugin`
  id("com.gradle.plugin-publish") version "0.16.0"
  id("org.jetbrains.dokka")
  `kotlin-dsl`
  `maven-publish`
  signing
}

task("compileAndroidStubLibTask", JavaCompile::class) {
  source(file("src/stub/java"))
  classpath = project.files(getAndroidJar(CommonConfig.compileSdk))
  destinationDirectory.set(File(project.buildDir, "/defensor/tmp/androidStubLib"))
}
task("generateAndroidStubJar", Jar::class) {
  archiveBaseName.set("defensor-android-stub")
  archiveVersion.set(CommonConfig.DEFENSOR_VERSION)
  from(tasks.getByName("compileAndroidStubLibTask"))
  include("**/*.class")
}

fun getAndroidJar(compileSdkVersion: Int): String {
  var androidSdkDir = Environment.instance.getVariable(Environment.EnvironmentVariable.ANDROID_PREFS_ROOT)
  if (androidSdkDir.isNullOrEmpty()) {
    val localProperties = rootProject.file("local.properties")
    if (localProperties.exists()) {
      val properties = Properties()
      properties.load(localProperties.inputStream())
      androidSdkDir = properties.getProperty("sdk.dir")
    }
  }
  if (androidSdkDir.isNullOrEmpty()) {
    throw StopExecutionException("please declares your 'sdk.dir' to file 'local.properties'")
  }
  val path = "platforms${File.separator}android-${compileSdkVersion}${File.separator}android.jar"
  return File(androidSdkDir.toString(), path).absolutePath
}

dependencies {
  compileOnly("com.android.tools.build:gradle:${CommonConfig.AGP_VERSION}")
  compileOnly(kotlin("gradle-plugin", CommonConfig.KOTLIN_VERSION))
  implementation(gradleApi())
  implementation("org.ow2.asm:asm-tree:9.2")

  val stub = tasks.getByName("generateAndroidStubJar").outputs.files
  implementation(stub)
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
