package org.polyfrost.elementa.shaders

import org.polyfrost.universal.UGraphics

@Deprecated("Use UniversalCraft's UShader instead.")
object Shaders {
    val newShaders: Boolean get() = UGraphics.areShadersSupported()
}
