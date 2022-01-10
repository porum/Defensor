plugins {
  id("com.android.library")
  id("org.jetbrains.dokka") version "1.5.30"
}

android {
  compileSdk = rootProject.ext["compileVersion"] as Int
  defaultConfig {
    minSdk = rootProject.ext["minVersion"] as Int
    targetSdk = rootProject.ext["targetVersion"] as Int
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildTypes {
    val release by getting {

    }
  }
}


dependencies {
  compileOnly("androidx.recyclerview:recyclerview:1.2.1")
  compileOnly("androidx.fragment:fragment:1.3.6")
  compileOnly("com.google.android.material:material:1.4.0")
}