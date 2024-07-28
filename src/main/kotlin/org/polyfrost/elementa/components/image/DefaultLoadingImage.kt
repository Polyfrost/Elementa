package org.polyfrost.elementa.components.image

import org.polyfrost.elementa.utils.drawTexture
import org.polyfrost.universal.UGraphics
import org.polyfrost.universal.UMatrixStack
import org.polyfrost.universal.utils.ReleasedDynamicTexture
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object DefaultLoadingImage : ImageProvider {
    private var loadingImage: BufferedImage? = ImageIO.read(this::class.java.getResourceAsStream("/loading.png"))
    private lateinit var loadingTexture: ReleasedDynamicTexture

    override fun drawImage(
        matrixStack: UMatrixStack,
        x: Double,
        y: Double,
        width: Double,
        height: Double,
        color: Color,
    ) {
        if (!DefaultLoadingImage::loadingTexture.isInitialized) {
            loadingTexture = UGraphics.getTexture(loadingImage!!)
            loadingImage = null
        }

        drawTexture(
            matrixStack,
            loadingTexture,
            color,
            x,
            y,
            width,
            height,
            textureMinFilter = GL11.GL_LINEAR,
            textureMagFilter = GL11.GL_LINEAR,
        )
    }
}
