# Defensor

[![license](https://img.shields.io/badge/license-Apache--2.0-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![defensor](https://img.shields.io/badge/defensor-1.0.8-brightgreen.svg)](https://search.maven.org/artifact/io.github.porum/defensor/1.0.8/aar)
[![defensor-gradle-plugin](https://img.shields.io/badge/defensor--gradle--plugin-1.0.8-brightgreen.svg)](https://search.maven.org/artifact/io.github.porum/defensor-gradle-plugin/1.0.8/jar)

Defensor is a gradle-plugin that can reduce Android App crashes.

## Usage

To add a dependency on `defensor`, you must add the Maven Central repository to your project.

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

```groovy
dependencies {
    implementation "io.github.porum:defensor:$version"
}
```

To add `defensor-gradle-plugin` to your project, include the following `classpath` in your top level `build.gradle` file:

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "io.github.porum:defensor-gradle-plugin:$version"
    }
}
```

To generate Java language code suitable for Java or mixed Java and Kotlin modules, add this line to **your app or module's** `build.gradle` file:

```groovy
apply plugin: "defensor"
```

