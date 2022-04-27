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
  implementation(project(":sample-lib"))
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("com.google.android.material:material:1.5.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.3")
}