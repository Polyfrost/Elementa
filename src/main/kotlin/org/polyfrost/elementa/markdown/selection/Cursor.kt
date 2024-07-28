package org.polyfrost.elementa.markdown.selection

import org.polyfrost.elementa.components.UIBlock
import org.polyfrost.elementa.markdown.DrawState
import org.polyfrost.elementa.markdown.MarkdownComponent
import org.polyfrost.elementa.markdown.drawables.Drawable
import org.polyfrost.universal.UMatrixStack
import java.awt.Color

abstract class Cursor<T : Drawable>(val target: T) {
    protected open val xBase = target.x
    protected open val yBase = target.y
    protected val height = target.height.toDouble()
    protected val width = height / 9.0

    @Deprecated(UMatrixStack.Compat.DEPRECATED, ReplaceWith("draw(matrixStack, state)"))
    fun draw(state: DrawState) = draw(UMatrixStack(), state)

    fun draw(matrixStack: UMatrixStack, state: DrawState) {
        if (!MarkdownComponent.DEBUG)
            return

        UIBlock.drawBlockSized(
            matrixStack,
            Color.RED,
            (xBase + state.xShift).toDouble(),
            (yBase + state.yShift).toDouble(),
            width,
            height
        )
    }

    abstract operator fun compareTo(other: Cursor<*>): Int
}
