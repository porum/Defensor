plugins {
  id("com.android.library")
  id("org.jetbrains.dokka") version "1.5.0"
}

android {

  defaultConfig {
    testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
  }

  buildTypes {
    val release by getting {
      minifyEnabled(false)
    }
  }
}


dependencies {
  compileOnly("androidx.recyclerview:recyclerview:1.2.1")
  compileOnly("androidx.fragment:fragment:1.3.4")
  compileOnly("com.google.android.material:material:1.4.0")
  testImplementation("junit:junit:4.+")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}