package cc.polyfrost.oneconfig.libs.elementa.markdown.drawables

import cc.polyfrost.oneconfig.libs.elementa.markdown.DrawState
import cc.polyfrost.oneconfig.libs.elementa.markdown.MarkdownComponent
import cc.polyfrost.oneconfig.libs.elementa.markdown.selection.Cursor
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack

/**
 * A hard break is two or more line breaks between lines of
 * markdown text.
 */
class HardBreakDrawable(md: MarkdownComponent) : Drawable(md) {
    override fun layoutImpl(x: Float, y: Float, width: Float): Layout {
        TODO("Not yet implemented")
    }

    override fun draw(matrixStack: UMatrixStack, state: DrawState) {
        TODO("Not yet implemented")
    }

    override fun cursorAt(mouseX: Float, mouseY: Float, dragged: Boolean, mouseButton: Int): Cursor<*> {
        TODO("Not yet implemented")
    }

    override fun cursorAtStart(): Cursor<*> {
        TODO("Not yet implemented")
    }

    override fun cursorAtEnd(): Cursor<*> {
        TODO("Not yet implemented")
    }

    override fun selectedText(asMarkdown: Boolean): String {
        TODO("Not yet implemented")
    }
}
