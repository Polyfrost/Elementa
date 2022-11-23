package cc.polyfrost.oneconfig.libs.elementa.components.image

import cc.polyfrost.oneconfig.libs.universal.utils.ReleasedDynamicTexture

interface CacheableImage {

    fun supply(image: CacheableImage)

    fun applyTexture(texture: ReleasedDynamicTexture?)

}