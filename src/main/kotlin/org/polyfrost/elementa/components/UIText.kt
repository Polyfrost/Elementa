package org.polyfrost.elementa.components

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.UIConstraints
import org.polyfrost.elementa.constraints.CenterConstraint
import org.polyfrost.elementa.dsl.width
import org.polyfrost.elementa.state.BasicState
import org.polyfrost.elementa.state.MappedState
import org.polyfrost.elementa.state.State
import org.polyfrost.elementa.state.pixels
import org.polyfrost.universal.UGraphics
import org.polyfrost.universal.UMatrixStack
import java.awt.Color

/**
 * Simple text component that draws its given `text` at the scale determined by
 * this component's width & height constraints.
 */
open class UIText
constructor(
    text: State<String>,
    shadow: State<Boolean> = BasicState(true),
    shadowColor: State<Color?> = BasicState(null)
) : UIComponent() {
    @JvmOverloads constructor(
        text: String = "",
        shadow: Boolean = true,
        shadowColor: Color? = null
    ) : this(BasicState(text), BasicState(shadow), BasicState(shadowColor))

    private val textState: MappedState<String, String> = text.map { it } // extra map so we can easily rebind it
    private val shadowState: MappedState<Boolean, Boolean> = shadow.map { it }
    private val shadowColorState: MappedState<Color?, Color?> = shadowColor.map { it }
    private val textScaleState = constraints.asState { getTextScale() }
    /** Guess on whether we should be trying to center or top-align this component. See [BELOW_LINE_HEIGHT]. */
    private val verticallyCenteredState = constraints.asState { y is CenterConstraint }
    private val fontProviderState = constraints.asState { fontProvider }
    private var textWidthState = textState.zip(textScaleState.zip(fontProviderState)).map { (text, opts) ->
        val (textScale, fontProvider) = opts
        text.width(textScale, fontProvider) / textScale
    }



    private fun <T> UIConstraints.asState(selector: UIConstraints.() -> T) = BasicState(selector(constraints)).also {
        constraints.addObserver { _, _ -> it.set(selector(constraints)) }
    }

    init {
        setWidth(textWidthState.pixels())
        setHeight(shadowState.zip(verticallyCenteredState.zip(fontProviderState)).map { (shadow, opts) ->
            val (verticallyCentered, fontProvider) = opts
            val above = (if (verticallyCentered) fontProvider.getBelowLineHeight() else 0f)
            val center = fontProvider.getBaseLineHeight()
            val below = fontProvider.getBelowLineHeight() + (if (shadow) fontProvider.getShadowHeight() else 0f)
            above + center + below
        }.pixels())
    }

    fun bindText(newTextState: State<String>) = apply {
        textState.rebind(newTextState)
    }

    fun bindShadow(newShadowState: State<Boolean>) = apply {
        shadowState.rebind(newShadowState)
    }

    fun bindShadowColor(newShadowColorState: State<Color?>) = apply {
        shadowColorState.rebind(newShadowColorState)
    }

    fun getText() = textState.get()
    fun setText(text: String) = apply { textState.set(text) }

    fun getShadow() = shadowState.get()
    fun setShadow(shadow: Boolean) = apply { shadowState.set(shadow) }

    @Deprecated("Wrong return type", level = DeprecationLevel.HIDDEN)
    @JvmName("getShadowColor")
    fun getShadowColorState(): State<Color?> = shadowColorState

    fun getShadowColor(): Color? = shadowColorState.get()
    fun setShadowColor(shadowColor: Color?) = apply { shadowColorState.set(shadowColor) }

    /**
     * Returns the text width if no scale is applied to the text
     */
    fun getTextWidth() = textWidthState.get()

    override fun getWidth(): Float {
        return super.getWidth() * getTextScale()
    }

    override fun getHeight(): Float {
        return super.getHeight() * getTextScale()
    }

    override fun draw(matrixStack: UMatrixStack) {
        val textWidth = textWidthState.get()

        // If you're wondering why we check if the text's width is 0 instead of if the string is empty:
        // It's better to check the width derived from the font provider, as the string may just be full of characters
        // that can't be rendered (as they aren't supported by current font).
        // This check prevents issues from occurring later, e.g. when calculating the scale of the text.
        if (textWidth == 0f)
            return

        beforeDrawCompat(matrixStack)

        val scale = getWidth() / textWidth
        val x = getLeft()
        val y = getTop() + (if (verticallyCenteredState.get()) fontProviderState.get().getBelowLineHeight() * scale else 0f)
        val color = getColor()

        // We aren't visible, don't draw
        if (color.alpha <= 10) {
            return super.draw(matrixStack)
        }

        UGraphics.enableBlend()

        val shadow = shadowState.get()
        val shadowColor = shadowColorState.get()
        getFontProvider().drawString(
            matrixStack,
            textState.get(), color, x, y,
            10f, scale, shadow, shadowColor
        )
        super.draw(matrixStack)
    }


}
