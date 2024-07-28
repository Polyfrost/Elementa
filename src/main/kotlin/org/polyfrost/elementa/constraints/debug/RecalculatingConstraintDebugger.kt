package org.polyfrost.elementa.constraints.debug

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.SuperConstraint

internal class RecalculatingConstraintDebugger(
    private val inner: ConstraintDebugger = NoopConstraintDebugger(),
) : ConstraintDebugger {
    private val visited = mutableSetOf<SuperConstraint<*>>()

    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (visited.add(constraint)) {
            constraint.recalculate = true
        }
        return inner.evaluate(constraint, type, component)
    }
}