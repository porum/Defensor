import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

apply<MavenPublishPlugin>()
apply<SigningPlugin>()

afterEvaluate {
  val isAndroid = plugins.hasPlugin("com.android.library")

  val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    if (isAndroid) {
      val sourceSet = project.the<LibraryExtension>().sourceSets["main"]
      from(sourceSet.java.getSourceFiles())
      if (plugins.hasPlugin("kotlin-android")) {
        from(sourceSet.withConvention(KotlinSourceSet::class) { kotlin })
      }
    } else {
      from(project.the<SourceSetContainer>()["main"].allSource)
    }
  }

  var javadoc: Javadoc? = null
  if (isAndroid) {
    val android = project.the<LibraryExtension>()
    val sourceSet = android.sourceSets["main"]
    android.libraryVariants.configureEach {
      if (name == "release") {
        val _javadoc by tasks.creating(Javadoc::class) {
          val javaCompile = javaCompileProvider.get()
          dependsOn(javaCompile)
          source = sourceSet.java.getSourceFiles()
          classpath += javaCompile.classpath
          classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
          options.encoding = "utf-8"
        }
        javadoc = _javadoc
      }
    }
  }

  val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    javadoc?.let {
      dependsOn(it)
      from(it.destinationDir)
    }
  }

  // https://docs.gradle.org/current/userguide/publishing_maven.html
  configure<PublishingExtension> {
    publications {
      create<MavenPublication>("binary") {

        artifact(sourceJar.flatMap { it.archiveFile }) {
          classifier = "sources"
          extension = "jar"
        }
        artifact(javadocJar)

        if (!isAndroid) {
          from(components["java"])
        } else {
          the<LibraryExtension>().libraryVariants.configureEach {
            if (name == "release") {
              artifact(packageLibraryProvider.flatMap { it.archiveFile }) {
                extension = "aar"
                builtBy(packageLibraryProvider)
              }

              pom.withXml {
                val deps = runtimeConfiguration.allDependencies.filterIsInstance<ModuleDependency>()
                if (deps.isNotEmpty()) {
                  asNode().appendNode("dependencies").apply {
                    deps.forEach {
                      with(appendNode("dependency")) {
                        appendNode("group", it.group)
                        appendNode("artifactId", it.name)
                        appendNode("version", it.version)
                        appendNode("scope", "compile")
                      }
                    }
                  }
                }
              }

            }
          }
        }

        pom {
          name.set("Defensor")
          description.set("Android Defensor")
          url.set("https://github.com/porum/Defensor")
          licenses {
            license {
              name.set("The Apache License, Version 2.0")
              url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
          }
          developers {
            developer {
              id.set("porum")
              name.set("sunguo.sun")
              email.set("sunguobao12@gmail.com")
            }
          }
          scm {
            url.set("https://github.com/porum/Defensor.git")
          }
        }

      }
    }

    val sonatypeUserName: String by project
    val sonatypePassword: String by project

    repositories {
      maven {
        setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        credentials {
          username = sonatypeUserName
          password = sonatypePassword
        }
      }
    }

  }

  configure<SigningExtension> {
    sign(the<PublishingExtension>().publications.findByName("binary"))
  }

  tasks.creating(Javadoc::class) {
    if (JavaVersion.current().isJava9Compatible) {
      (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
  }

}