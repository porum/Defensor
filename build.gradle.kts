ext {
  this["compileVersion"] = 31
  this["minVersion"] = 21
  this["targetVersion"] = 31
  this["buildTools"] = "32.0.0"
}

val commonConfigure = project.file("common.gradle.kts")

subprojects {
  repositories {
    google()
    mavenLocal()
    maven {
      setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    mavenCentral()
  }

  configurations.configureEach {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
  }

  apply(from = commonConfigure)
  if (name.startsWith("defensor")) {
    pluginManager.withPlugin("maven-publish") {

      val GROUP: String by project
      val VERSION: String by project
      val sonatypeUserName: String by project
      val sonatypePassword: String by project

      val publishExtension = extensions.getByType<PublishingExtension>()
      publishExtension.repositories {
        mavenLocal()
        maven {
          val url = if (VERSION.endsWith("-SNAPSHOT")) {
            "https://s01.oss.sonatype.org/content/repositories/snapshots/"
          } else {
            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
          }
          setUrl(url)
          credentials {
            username = sonatypeUserName
            password = sonatypePassword
          }
        }
      }

      publishExtension.publications.whenObjectAdded {
        check(this is MavenPublication) {
          "unexpected publication $this"
        }
        groupId = GROUP
        version = VERSION

        pom {
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
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}