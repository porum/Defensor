plugins {
  id("com.android.application")
  id("kotlin-android")
  id("defensor")
}

defensor {
  enable = true
  excludes = listOf(
    "android",
    "androidx",
    "kotlin",
    "kotlinx",
    "com.google",
    "com.panda912.defensor.samplelib.Lib"
  )
}

android {
  compileSdk = rootProject.ext["compileVersion"] as Int

  defaultConfig {
    minSdk = rootProject.ext["minVersion"] as Int
    targetSdk = rootProject.ext["targetVersion"] as Int

    applicationId = "com.panda912.defensor.sample"
    versionCode = 1
    versionName = "1.0.0"
  }

  buildTypes {
    val release by getting {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {
  implementation("io.github.porum:defensor:${project.version}")
}