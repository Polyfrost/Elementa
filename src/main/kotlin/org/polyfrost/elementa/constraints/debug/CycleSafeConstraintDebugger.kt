package org.polyfrost.elementa.constraints.debug

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.SuperConstraint

internal class CycleSafeConstraintDebugger(
    private val inner: ConstraintDebugger = NoopConstraintDebugger(),
) : ConstraintDebugger {
    private val stack = mutableSetOf<SuperConstraint<*>>()

    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (constraint in stack) {
            // Cycle detected! Let's just return some arbitrary value so we can continue.
            return 10f // non-0 so the component can still be highlighted
        }

        stack.add(constraint)
        try {
            return inner.evaluate(constraint, type, component)
        } finally {
            stack.remove(constraint)
        }
    }
}