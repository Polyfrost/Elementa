package cc.polyfrost.oneconfig.libs.elementa.constraints.debug

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.ConstraintType
import cc.polyfrost.oneconfig.libs.elementa.constraints.SuperConstraint

internal class NoopConstraintDebugger : ConstraintDebugger {
    override fun evaluate(constraint: SuperConstraint<Float>, type: ConstraintType, component: UIComponent): Float {
        if (constraint.recalculate) {
            constraint.cachedValue = invokeImpl(constraint, type, component)
            constraint.recalculate = false
        }

        return constraint.cachedValue
    }
}
