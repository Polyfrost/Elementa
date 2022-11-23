package cc.polyfrost.oneconfig.libs.elementa.components.inspector

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.components.TreeNode
import cc.polyfrost.oneconfig.libs.elementa.components.UIBlock
import cc.polyfrost.oneconfig.libs.elementa.components.UIText
import cc.polyfrost.oneconfig.libs.elementa.constraints.ChildBasedSizeConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.SiblingConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.TextAspectConstraint
import cc.polyfrost.oneconfig.libs.elementa.dsl.toConstraint
import cc.polyfrost.oneconfig.libs.elementa.dsl.childOf
import cc.polyfrost.oneconfig.libs.elementa.dsl.constrain
import cc.polyfrost.oneconfig.libs.elementa.dsl.pixels
import java.awt.Color

class InspectorNode(private val inspector: Inspector, val targetComponent: UIComponent) : TreeNode() {
    private val componentClassName = targetComponent.javaClass.simpleName.ifEmpty { "UnknownType" }
    private val componentDisplayName = targetComponent.componentName.let { if (it == componentClassName) it else "$componentClassName: $it" }
    private var wasHidden = false

    private val component: UIComponent = object : UIBlock(Color(0, 0, 0, 0)) {
        private val text = UIText(componentDisplayName).constrain {
            width = TextAspectConstraint()
        } childOf this

        override fun animationFrame() {
            super.animationFrame()

            val isCurrentlyHidden = targetComponent.parent != targetComponent && !targetComponent.parent.children.contains(
                targetComponent
            )
            if (isCurrentlyHidden && !wasHidden) {
                wasHidden = true
                text.setText("§r$componentDisplayName §7§o(Hidden)")
            } else if (!isCurrentlyHidden && wasHidden) {
                wasHidden = false
                text.setText(componentDisplayName)
            }
        }
    }.constrain {
        x = SiblingConstraint()
        y = 2.pixels()
        width = ChildBasedSizeConstraint()
        height = ChildBasedSizeConstraint()
    }.onMouseClick { event ->
        event.stopImmediatePropagation()
        toggleSelection()
    }

    internal fun toggleSelection() {
        inspector.selectedNode?.component?.setColor(Color(0, 0, 0, 0).toConstraint())

        if (inspector.selectedNode == this@InspectorNode) {
            inspector.setSelectedNode(null)
        } else {
            inspector.setSelectedNode(this@InspectorNode)
            component.setColor(Color(32, 78, 138).toConstraint())
        }
    }

    override fun getArrowComponent() = ArrowComponent(targetComponent.children.isEmpty())

    override fun getPrimaryComponent() = component
}
