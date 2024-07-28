package org.polyfrost.elementa.components

import org.polyfrost.elementa.components.inspector.ArrowComponent
import org.polyfrost.elementa.constraints.SiblingConstraint
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.dsl.pixels
import org.polyfrost.elementa.markdown.drawables.Drawable
import org.polyfrost.elementa.markdown.drawables.TextDrawable

internal class MarkdownNode(private val targetDrawable: Drawable) : TreeNode() {
    private val componentClassName = targetDrawable.javaClass.simpleName.ifEmpty { "UnknownType" }
    private val componentDisplayName =
        componentClassName + if (targetDrawable is TextDrawable) " \"${targetDrawable.formattedText}\"" else ""

    private val component = UIText(componentDisplayName).constrain {
        x = SiblingConstraint()
        y = 2.pixels
    }

    init {
        targetDrawable.children.forEach {
            addChild(MarkdownNode(it))
        }
    }
    override fun getArrowComponent() = ArrowComponent(targetDrawable.children.isEmpty())

    override fun getPrimaryComponent() = component
}
