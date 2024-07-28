package org.polyfrost.elementa.constraints.debug

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.SuperConstraint

internal class NoopConstraintDebugger : ConstraintDebugger {
    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (constraint.recalculate) {
            constraint.cachedValue = invokeImpl(constraint, type, component)
            constraint.recalculate = false
        }

        return constraint.cachedValue
    }
}
