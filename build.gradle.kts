import cc.polyfrost.gradle.multiversion.StripReferencesTransform.Companion.registerStripReferencesAttribute
import cc.polyfrost.gradle.util.*
import cc.polyfrost.gradle.util.RelocationTransform.Companion.registerRelocationAttribute

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.8.0"
    id("org.jetbrains.dokka") version "1.6.10" apply false
    id("cc.polyfrost.defaults")
}

kotlin.jvmToolchain {
    (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
}
tasks.compileKotlin.setJvmDefault("all-compatibility")

val internal by configurations.creating {
    val relocated = registerRelocationAttribute("internal-relocated") {
        relocate("org.dom4j", "cc.polyfrost.oneconfig.libs.elementa.impl.dom4j")
        relocate("org.commonmark", "cc.polyfrost.oneconfig.libs.elementa.impl.commonmark")
        remapStringsIn("org.dom4j.DocumentFactory")
        remapStringsIn("org.commonmark.internal.util.Html5Entities")
    }
    attributes { attribute(relocated, true) }
}

val common = registerStripReferencesAttribute("common") {
    excludes.add("net.minecraft")
}

dependencies {
    compileOnly(libs.kotlin.stdlib.jdk8)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.jetbrains.annotations)

    internal(libs.commonmark)
    internal(libs.commonmark.ext.gfm.strikethrough)
    internal(libs.commonmark.ext.ins)
    internal(libs.dom4j)
    implementation(prebundle(internal))

    // Depending on LWJGL3 instead of 2 so we can choose opengl bindings only
    compileOnly("org.lwjgl:lwjgl-opengl:3.3.1")
    // Depending on 1.8.9 for all of these because that's the oldest version we support
    compileOnly(libs.versions.universalcraft.map { "cc.polyfrost:universalcraft-1.8.9-forge:$it" }) {
        attributes { attribute(common, true) }
    }
    compileOnly("com.google.code.gson:gson:2.2.4")
}

apiValidation {
    ignoredProjects.add("platform")
    ignoredPackages.add("com.example")
    nonPublicMarkers.add("org.jetbrains.annotations.ApiStatus\$Internal")
}
