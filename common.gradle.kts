import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.AndroidBasePlugin

val isPublishLibrary = project.name.startsWith("defensor")


val GROUP: String by project
val VERSION: String by project
project.group = GROUP
project.version = VERSION

project.plugins.withType<AndroidBasePlugin> {
  afterEvaluate {
    configure<BaseExtension> {
      val compileVersion: Int by project
      compileSdkVersion(compileVersion)

      defaultConfig {
        val minVersion: Int by project
        val targetVersion: Int by project
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
        versionName = VERSION
      }
      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
      }

      if (isPublishLibrary && this is LibraryExtension) {
        defaultConfig.consumerProguardFile("proguard-rules.pro")
      }
    }
  }
}

project.plugins.withType<JavaPlugin> {
  configure<JavaPluginConvention> {
    this.targetCompatibility = JavaVersion.VERSION_1_8
    this.sourceCompatibility = JavaVersion.VERSION_1_8
  }
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}