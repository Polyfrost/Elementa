package cc.polyfrost.oneconfig.libs.elementa.constraints

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.components.UIText
import cc.polyfrost.oneconfig.libs.elementa.constraints.resolution.ConstraintVisitor
import cc.polyfrost.oneconfig.libs.universal.UGraphics
import java.lang.UnsupportedOperationException

/**
 * Sets the width/height to be a scale of the default text width and height
 */
class ScaledTextConstraint(var scale: Float) : SizeConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null


    override fun getWidthImpl(component: UIComponent): Float {
        return when (component) {
            is UIText -> scale * UGraphics.getStringWidth(component.getText())
            else -> throw IllegalAccessException("ScaledTextConstraint can only be used with UIText")
        }
    }

    override fun getHeightImpl(component: UIComponent): Float {
        return when(component) {
            is UIText -> scale * 9
            else -> throw IllegalAccessException("ScaledTextConstraint can only be used with UIText")
        }
    }

    override fun getRadiusImpl(component: UIComponent): Float {
        throw IllegalAccessException("ScaledTextConstraint cannot be used as a radius")
    }

    override fun to(component: UIComponent) = apply {
        throw UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context!")
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) { }
}
