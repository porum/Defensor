plugins {
  id("com.android.library")
  `maven-publish`
  signing
}

android {
  val compileVersion: Int by project
  compileSdk = compileVersion

  defaultConfig {
    val minVersion: Int by project
    val targetVersion: Int by project
    minSdk = minVersion
    targetSdk = targetVersion
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildTypes {
    val release by getting {
      isMinifyEnabled = false
    }
  }

//  // agp 7.1, withJavadocJar() occur dokka error.
//  // but this module not use dokka...
//  // agp 7.1.3 use dokka 1.4.32, project use dokka 1.6.21
//  publishing {
//    singleVariant("release") {
//      withJavadocJar()
//      withSourcesJar()
//    }
//  }

}

dependencies {
  compileOnly("androidx.recyclerview:recyclerview:1.2.1")
  compileOnly("androidx.fragment:fragment:1.3.4")
  compileOnly("com.google.android.material:material:1.4.0")
  testImplementation("junit:junit:4.+")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

afterEvaluate {
  val javadoc by tasks.registering(Javadoc::class) {
    android.libraryVariants.configureEach {
      if (name == "release") {
        val javaCompile = javaCompileProvider.get()
        dependsOn(javaCompile)
        source = android.sourceSets["main"].java.getSourceFiles()
        classpath += javaCompile.classpath
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
        options.encoding = "utf-8"
      }
    }
  }

  val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)
    from(javadoc.get().destinationDir)
    archiveClassifier.set("javadoc")
  }

  val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets["main"].java.getSourceFiles())
    if (plugins.hasPlugin("kotlin-android")) {
      from(android.sourceSets["main"].kotlin)
    }
    archiveClassifier.set("sources")
  }

  publishing {
    publications {
      create<MavenPublication>("Defensor") {
        // see gradle.properties need set android.disableAutomaticComponentCreation=false
        from(components["release"])
        artifact(sourceJar)
        artifact(javadocJar)

        pom {
          name.set("defensor")
          description.set("defensor library")
        }
      }
    }
  }

  signing {
    publishing.publications["Defensor"]
  }
}