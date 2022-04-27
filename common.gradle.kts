import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


val GROUP: String by project
val VERSION: String by project
project.group = GROUP
project.version = VERSION


project.plugins.withId("kotlin-android") {
  tasks.withType(KotlinCompile::class).configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
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