plugins {
  `java-library`
  `maven-publish`
  signing
}

//val javadoc by tasks.registering(Javadoc::class) {
//  source(sourceSets.main.get().java)
//  options.encoding = "utf-8"
//}

val javadoc = tasks.named<Javadoc>("javadoc") {
  source(sourceSets.main.get().java)
  options.encoding = "utf-8"
}

val javadocJar by tasks.registering(Jar::class) {
  dependsOn(javadoc)
  from(javadoc.get().destinationDir)
  archiveClassifier.set("javadoc")
}

val sourceJar by tasks.registering(Jar::class) {
  from(sourceSets.main.get().allSource)
  archiveClassifier.set("sources")
}

publishing {
  publications {
    create<MavenPublication>("DefensorStubAndroid") {
      from(components["java"])
      artifact(sourceJar)
      artifact(javadocJar)

      pom {
        name.set("defensor-stub-android")
        description.set("This library provide android api for defensor-gradle-plugin")
      }
    }
  }
}

signing {
  sign(extensions.getByType<PublishingExtension>().publications)
}