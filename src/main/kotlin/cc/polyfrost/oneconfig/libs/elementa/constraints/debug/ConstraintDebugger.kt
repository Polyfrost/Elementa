package cc.polyfrost.oneconfig.libs.elementa.constraints.debug

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.ConstraintType
import cc.polyfrost.oneconfig.libs.elementa.constraints.HeightConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.RadiusConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.SuperConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.WidthConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.XConstraint
import cc.polyfrost.oneconfig.libs.elementa.constraints.YConstraint
import cc.polyfrost.oneconfig.libs.elementa.utils.roundToRealPixels

internal interface ConstraintDebugger {
    fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float

    fun invokeImpl(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float =
        when (type) {
            ConstraintType.X -> (constraint as XConstraint).getXPositionImpl(component).roundToRealPixels()
            ConstraintType.Y -> (constraint as YConstraint).getYPositionImpl(component).roundToRealPixels()
            ConstraintType.WIDTH -> (constraint as WidthConstraint).getWidthImpl(component).roundToRealPixels()
            ConstraintType.HEIGHT -> (constraint as HeightConstraint).getHeightImpl(component).roundToRealPixels()
            ConstraintType.RADIUS -> (constraint as RadiusConstraint).getRadiusImpl(component)
            else -> throw UnsupportedOperationException()
        }
}

internal var constraintDebugger: ConstraintDebugger? = null

internal inline fun withDebugger(debugger: ConstraintDebugger, block: () -> Unit) {
    val prevDebugger = constraintDebugger
    constraintDebugger = debugger
    try {
        block()
    } finally {
        constraintDebugger = prevDebugger
    }
}
