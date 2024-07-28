package org.polyfrost.elementa.constraints.resolution

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.SuperConstraint

data class ResolverNode(
    val component: UIComponent,
    val constraint: SuperConstraint<*>,
    val constraintType: ConstraintType
)