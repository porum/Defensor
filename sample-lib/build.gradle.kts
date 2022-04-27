plugins {
  id("com.android.library")
  id("kotlin-android")
}

android {
  compileSdk = rootProject.ext["compileVersion"] as Int
  
  defaultConfig {
    minSdk = rootProject.ext["minVersion"] as Int
    targetSdk = rootProject.ext["targetVersion"] as Int

    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    val release by getting {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {
  api("io.github.porum:defensor:${project.version}")
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("com.google.android.material:material:1.5.0")
  implementation("com.google.code.gson:gson:2.8.9")
}