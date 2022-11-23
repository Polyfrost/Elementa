package cc.polyfrost.oneconfig.libs.elementa.shaders

import cc.polyfrost.oneconfig.libs.universal.UGraphics

@Deprecated("Use UniversalCraft's UShader instead.")
object Shaders {
    val newShaders: Boolean get() = UGraphics.areShadersSupported()
}
