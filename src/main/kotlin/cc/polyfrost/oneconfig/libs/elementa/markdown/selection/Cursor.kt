package cc.polyfrost.oneconfig.libs.elementa.markdown.selection

import cc.polyfrost.oneconfig.libs.elementa.components.UIBlock
import cc.polyfrost.oneconfig.libs.elementa.markdown.DrawState
import cc.polyfrost.oneconfig.libs.elementa.markdown.MarkdownComponent
import cc.polyfrost.oneconfig.libs.elementa.markdown.drawables.Drawable
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
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
