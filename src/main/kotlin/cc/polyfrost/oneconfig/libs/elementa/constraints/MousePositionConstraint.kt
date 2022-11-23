package cc.polyfrost.oneconfig.libs.elementa.constraints

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.resolution.ConstraintVisitor

class MousePositionConstraint : PositionConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun getXPositionImpl(component: UIComponent): Float {
        return UIComponent.getMouseX()
    }

    override fun getYPositionImpl(component: UIComponent): Float {
        return UIComponent.getMouseY()
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) { }
}
