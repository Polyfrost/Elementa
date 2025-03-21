package org.polyfrost.elementa.constraints

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.resolution.ConstraintVisitor

class AdditiveConstraint(
    val constraint1: SuperConstraint<Float>,
    val constraint2: SuperConstraint<Float>
) : MasterConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun animationFrame() {
        super.animationFrame()
        constraint1.animationFrame()
        constraint2.animationFrame()
    }

    override fun getXPositionImpl(component: UIComponent): Float {
        return (constraint1 as XConstraint).getXPosition(component) +
                (constraint2 as XConstraint).getXPosition(component) -
                (constrainTo ?: component.parent).getLeft()
    }

    override fun getYPositionImpl(component: UIComponent): Float {
        return (constraint1 as YConstraint).getYPosition(component) +
                (constraint2 as YConstraint).getYPosition(component) -
                (constrainTo ?: component.parent).getTop()
    }

    override fun getWidthImpl(component: UIComponent): Float {
        return (constraint1 as WidthConstraint).getWidth(component) +
                (constraint2 as WidthConstraint).getWidth(component)
    }

    override fun getHeightImpl(component: UIComponent): Float {
        return (constraint1 as HeightConstraint).getHeight(component) +
                (constraint2 as HeightConstraint).getHeight(component)
    }

    override fun getRadiusImpl(component: UIComponent): Float {
        return (constraint1 as RadiusConstraint).getRadius(component) +
                (constraint2 as RadiusConstraint).getRadius(component)
    }

    override fun to(component: UIComponent): SuperConstraint<Float> {
        throw UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context, please apply this to the components beforehand.")
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
        constraint1.visit(visitor, type, setNewConstraint = false)
        constraint2.visit(visitor, type, setNewConstraint = false)
    }
}