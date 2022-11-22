pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.polyfrost.cc/releases")
    }
    plugins {
        val egtVersion = "0.1.22"
        id("cc.polyfrost.defaults") version egtVersion
        id("cc.polyfrost.multi-version.root") version egtVersion
        id("cc.polyfrost.multi-version.api-validation") version egtVersion
    }
}

rootProject.name = "Elementa"

include(":platform")
project(":platform").apply {
    projectDir = file("versions/")
    buildFileName = "root.gradle.kts"
}

listOf(
    "1.8.9-forge",
    "1.8.9-fabric",
    "1.12.2-forge",
    "1.12.2-fabric",
    "1.15.2-forge",
    "1.16.2-forge",
    "1.16.2-fabric",
    "1.17.1-fabric",
    "1.17.1-forge",
    "1.18.1-fabric",
    "1.18.1-forge",
).forEach { version ->
    include(":platform:$version")
    project(":platform:$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../build.gradle.kts"
    }
}

