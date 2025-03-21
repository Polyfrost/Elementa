package org.polyfrost.elementa.constraints.resolution

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.components.Window
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.SuperConstraint

class ConstraintVisitor(
    private val graph: DirectedAcyclicGraph<ResolverNode>,
    val component: UIComponent
) {
    private lateinit var currentConstraint: SuperConstraint<*>
    private lateinit var currentConstraintType: ConstraintType

    fun setConstraint(constraint: SuperConstraint<*>, type: ConstraintType) {
        currentConstraint = constraint
        currentConstraintType = type
    }

    fun visitParent(type: ConstraintType) {
        if (!component.hasParent || component is Window || component.parent is Window)
            return

        graph.addEdge(
            ResolverNode(component, currentConstraint, currentConstraintType),
            ResolverNode(component.parent, component.parent.constraints.getConstraint(type), type)
        )
    }

    fun visitSelf(type: ConstraintType) {
        graph.addEdge(
            ResolverNode(component, currentConstraint, currentConstraintType),
            ResolverNode(component, component.constraints.getConstraint(type), type)
        )
    }

    fun visitSibling(type: ConstraintType, index: Int) {
        if (!component.hasParent) {
            throw IllegalStateException("""
                Error during Elementa constraint validation: the current component has no parent,
                but visitSibling was called. This shouldn't be possible -- if you are seeing this,
                please notify an Elementa developer ASAP!
            """.trimIndent())
        }

        val sibling = component.parent.children[index]

        graph.addEdge(
            ResolverNode(component, currentConstraint, currentConstraintType),
            ResolverNode(sibling, sibling.constraints.getConstraint(type), type)
        )
    }

    fun visitChildren(type: ConstraintType) {
        val currNode = ResolverNode(component, currentConstraint, currentConstraintType)

        component.children.forEach {
            graph.addEdge(
                currNode,
                ResolverNode(it, it.constraints.getConstraint(type), type)
            )
        }
    }
}
