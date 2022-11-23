package cc.polyfrost.oneconfig.libs.elementa.constraints.resolution

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.ConstraintType
import cc.polyfrost.oneconfig.libs.elementa.constraints.SuperConstraint

data class ResolverNode(
    val component: UIComponent,
    val constraint: SuperConstraint<*>,
    val constraintType: ConstraintType
)