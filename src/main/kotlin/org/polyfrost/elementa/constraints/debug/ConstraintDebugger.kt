package org.polyfrost.elementa.constraints.debug

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.HeightConstraint
import org.polyfrost.elementa.constraints.RadiusConstraint
import org.polyfrost.elementa.constraints.SuperConstraint
import org.polyfrost.elementa.constraints.WidthConstraint
import org.polyfrost.elementa.constraints.XConstraint
import org.polyfrost.elementa.constraints.YConstraint
import org.polyfrost.elementa.utils.roundToRealPixels

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
