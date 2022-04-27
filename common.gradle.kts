import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.AndroidBasePlugin


val GROUP: String by project
val VERSION: String by project
project.group = GROUP
project.version = VERSION

project.plugins.withType<JavaPlugin> {
  configure<JavaPluginConvention> {
    this.targetCompatibility = JavaVersion.VERSION_1_8
    this.sourceCompatibility = JavaVersion.VERSION_1_8
  }
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}