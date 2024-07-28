package org.polyfrost.elementa.components

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.dsl.toConstraint
import org.polyfrost.elementa.dsl.pixels
import org.polyfrost.elementa.utils.readFromLegacyShader
import org.polyfrost.universal.UMatrixStack
import org.polyfrost.universal.shader.BlendState
import org.polyfrost.universal.shader.Float2Uniform
import org.polyfrost.universal.shader.FloatUniform
import org.polyfrost.universal.shader.UShader
import java.awt.Color

/**
 * Simple component that uses shaders to draw a circle. This component
 * takes a radius instead of a height/width!
 *
 * @param radius circle radius
 * @param color circle color
 * @param steps unused, kept for backwards compatibility
 */
class UICircle @JvmOverloads constructor(radius: Float = 0f, color: Color = Color.WHITE, var steps: Int = 0) :
    UIComponent() {
    init {
        setColor(color.toConstraint())
        setRadius(radius.pixels())
    }

    override fun getLeft(): Float {
        return constraints.getX() - getRadius()
    }

    override fun getTop(): Float {
        return constraints.getY() - getRadius()
    }

    override fun getWidth(): Float {
        return getRadius() * 2
    }

    override fun getHeight(): Float {
        return getRadius() * 2
    }

    override fun isPositionCenter(): Boolean {
        return true
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDraw(matrixStack)

        val x = constraints.getX()
        val y = constraints.getY()
        val r = getRadius()

        val color = getColor()
        if (color.alpha == 0) return super.draw(matrixStack)

        drawCircle(matrixStack, x, y, r, color)

        super.draw(matrixStack)
    }

    companion object {
        private lateinit var shader: UShader
        private lateinit var shaderRadiusUniform: FloatUniform
        private lateinit var shaderCenterPositionUniform: Float2Uniform

        fun initShaders() {
            if (Companion::shader.isInitialized)
                return

            shader = UShader.readFromLegacyShader("rect", "circle", BlendState.NORMAL)
            if (!shader.usable) {
                println("Failed to load Elementa UICircle shader")
                return
            }
            shaderRadiusUniform = shader.getFloatUniform("u_Radius")
            shaderCenterPositionUniform = shader.getFloat2Uniform("u_CenterPos")
        }

        @Deprecated(
            UMatrixStack.Compat.DEPRECATED,
            ReplaceWith("drawCircle(matrixStack, centerX, centerY, radius, color)"),
        )
        fun drawCircle(centerX: Float, centerY: Float, radius: Float, color: Color) =
            drawCircle(UMatrixStack(), centerX, centerY, radius, color)

        fun drawCircle(matrixStack: UMatrixStack, centerX: Float, centerY: Float, radius: Float, color: Color) {
            if (!Companion::shader.isInitialized || !shader.usable)
                return

            shader.bind()
            shaderRadiusUniform.setValue(radius)
            shaderCenterPositionUniform.setValue(centerX, centerY)

            UIBlock.drawBlockWithActiveShader(
                matrixStack,
                color,
                (centerX - radius).toDouble(),
                (centerY - radius).toDouble(),
                (centerX + radius).toDouble(),
                (centerY + radius).toDouble()
            )

            shader.unbind()
        }
    }
}
