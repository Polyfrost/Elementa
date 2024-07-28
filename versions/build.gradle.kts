import org.polyfrost.gradle.multiversion.excludeKotlinDefaultImpls
import org.polyfrost.gradle.multiversion.mergePlatformSpecifics
import org.polyfrost.gradle.util.*

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.polyfrost.multi-version")
    id("org.polyfrost.defaults")
    id("org.polyfrost.defaults.maven-publish")
}

group = "cc.polyfrost"

java.withSourcesJar()
tasks.compileKotlin.setJvmDefault(if (platform.mcVersion >= 11400) "all" else "all-compatibility")
loom.noServerRunConfigs()

val common by configurations.creating
configurations.compileClasspath { extendsFrom(common) }
configurations.runtimeClasspath { extendsFrom(common) }

repositories {
    maven("https://repo.polyfrost.org/releases")
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.reflect)
    compileOnly(libs.jetbrains.annotations)

    modApi(libs.versions.universalcraft.map { "cc.polyfrost:universalcraft-$platform:$it" }) {
        exclude(group = "org.jetbrains.kotlin")
    }

    common(project(":"))

    if (platform.isFabric) {
        val fabricApiVersion = when (platform.mcVersion) {
            10809 -> "1.8.0+1.8.9"
            11202 -> "1.8.0+1.12.2"
            11404 -> "0.4.3+build.247-1.14"
            11502 -> "0.5.1+build.294-1.15"
            11601 -> "0.14.0+build.371-1.16"
            11605 -> "0.42.0+1.16"
            11701 -> "0.38.1+1.17"
            11802 -> "0.67.0+1.18.2"
            else -> throw GradleException("Unsupported platform $platform")
        }
        if (platform.isLegacyFabric) {
            modLocalRuntime(modCompileOnly("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:$fabricApiVersion") {
                exclude(module = "legacy-fabric-entity-events-v1")
            })
            runtimeOnly(compileOnly("org.apache.logging.log4j:log4j-core:2.8.1")!!)
            runtimeOnly(compileOnly("org.apache.logging.log4j:log4j-api:2.8.1")!!)
        } else {
            val fabricApiModules = mutableListOf(
                "api-base",
                "networking-v0",
                "keybindings-v0",
                "resource-loader-v0",
                "lifecycle-events-v1",
            )
            if (platform.mcVersion >= 11600) {
                fabricApiModules.add("key-binding-api-v1")
            }
            fabricApiModules.forEach { module ->
                // Using this combo to add it to our deps but not to our maven publication cause it's only for the example
                modLocalRuntime(modCompileOnly(fabricApi.module("fabric-$module", fabricApiVersion))!!)
            }
        }
    }
}

tasks.processResources {
    filesMatching(listOf("fabric.mod.json")) {
        filter { it.replace("\"com.example.examplemod.ExampleMod\"", "") }
    }
}

tasks.dokkaHtml {
    moduleName.set("Elementa $name")
}

tasks.jar {
    dependsOn(common)
    from({ common.map { zipTree(it) } })
    mergePlatformSpecifics()

    // We build the common module with legacy default impl for backwards compatibility, but we only need those for
    // 1.12.2 and older. Newer versions have never shipped with legacy default impl.
    if (platform.mcVersion >= 11400) {
        excludeKotlinDefaultImpls()
    }

    exclude("com/example/examplemod/**")
    exclude("META-INF/mods.toml")
    exclude("mcmod.info")
    exclude("kotlin/**")
}

tasks.named<Jar>("sourcesJar") {
    from(project(":").sourceSets.main.map { it.allSource })
}