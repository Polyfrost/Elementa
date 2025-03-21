pluginManagement {
    repositories {
        maven("https://repo.polyfrost.org/releases")
    }
    plugins {
        val egtVersion = "0.6.6"
        id("org.polyfrost.defaults") version egtVersion
        id("org.polyfrost.multi-version.root") version egtVersion
        id("org.polyfrost.multi-version.api-validation") version egtVersion
    }
}

rootProject.name = "Elementa"

include(":platform")
project(":platform").apply {
    projectDir = file("versions/")
    buildFileName = "root.gradle.kts"
}
include(":unstable:statev2")
include(":unstable:layoutdsl")

listOf(
    "1.8.9-forge",
    "1.8.9-fabric",
    "1.12.2-forge",
    "1.12.2-fabric",
    "1.16.5-forge",
    "1.16.5-fabric",
    "1.17.1-fabric",
    "1.17.1-forge",
    "1.18.2-fabric",
    "1.18.2-forge",
).forEach { version ->
    include(":platform:$version")
    project(":platform:$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../build.gradle.kts"
    }
}

