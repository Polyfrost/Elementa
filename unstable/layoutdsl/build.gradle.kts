import org.polyfrost.gradle.multiversion.StripReferencesTransform.Companion.registerStripReferencesAttribute
import org.polyfrost.gradle.util.setJvmDefault

plugins {
    kotlin("jvm")
    id("org.polyfrost.defaults")
    id("org.polyfrost.defaults.maven-publish")
}

group = "org.polyfrost"

dependencies {
    compileOnly(project(":"))
    api(project(":unstable:statev2"))

    val common = registerStripReferencesAttribute("common") {
        excludes.add("net.minecraft")
    }
    compileOnly(libs.versions.universalcraft.map { "org.polyfrost:universalcraft-1.8.9-forge:$it" }) {
        attributes { attribute(common, true) }
    }
}
tasks.compileKotlin.setJvmDefault("all")

kotlin.jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
}

publishing {
    publications {
        named<MavenPublication>("maven") {
            artifactId = "elementa-unstable-${project.name}"
        }
    }
}