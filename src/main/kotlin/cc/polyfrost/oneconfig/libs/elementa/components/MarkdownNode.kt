package cc.polyfrost.oneconfig.libs.elementa.components

import cc.polyfrost.oneconfig.libs.elementa.components.inspector.ArrowComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.SiblingConstraint
import cc.polyfrost.oneconfig.libs.elementa.dsl.constrain
import cc.polyfrost.oneconfig.libs.elementa.dsl.pixels
import cc.polyfrost.oneconfig.libs.elementa.markdown.drawables.Drawable
import cc.polyfrost.oneconfig.libs.elementa.markdown.drawables.TextDrawable

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
