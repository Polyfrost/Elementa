package cc.polyfrost.oneconfig.libs.elementa.constraints

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.components.UIImage
import cc.polyfrost.oneconfig.libs.elementa.constraints.resolution.ConstraintVisitor
import java.lang.UnsupportedOperationException

/**
 * Sets the width/height to be the correct aspect of its own height/width respectively.
 */
class ImageAspectConstraint : WidthConstraint, HeightConstraint {
    override var cachedValue = 0f
    override var recalculate = true
    override var constrainTo: UIComponent? = null

    override fun getWidthImpl(component: UIComponent): Float {
        val image = component as? UIImage ?: throw IllegalStateException("ImageAspectConstraint can only be used in UIImage components")
        return component.getHeight() * image.imageWidth / image.imageHeight
    }

    override fun getHeightImpl(component: UIComponent): Float {
        val image = component as? UIImage ?: throw IllegalStateException("ImageAspectConstraint can only be used in UIImage components")
        return component.getWidth() * image.imageHeight / image.imageWidth
    }

    override fun to(component: UIComponent) = apply {
        throw UnsupportedOperationException("Constraint.to(UIComponent) is not available in this context!")
    }

    override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
        when (type) {
            ConstraintType.WIDTH -> visitor.visitSelf(ConstraintType.HEIGHT)
            ConstraintType.HEIGHT -> visitor.visitSelf(ConstraintType.WIDTH)
            else -> throw IllegalArgumentException(type.prettyName)
        }
    }
}
