package org.polyfrost.elementa.components.inspector

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.components.TreeNode
import org.polyfrost.elementa.components.UIBlock
import org.polyfrost.elementa.components.UIText
import org.polyfrost.elementa.constraints.ChildBasedSizeConstraint
import org.polyfrost.elementa.constraints.SiblingConstraint
import org.polyfrost.elementa.constraints.TextAspectConstraint
import org.polyfrost.elementa.dsl.toConstraint
import org.polyfrost.elementa.dsl.childOf
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.dsl.pixels
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
