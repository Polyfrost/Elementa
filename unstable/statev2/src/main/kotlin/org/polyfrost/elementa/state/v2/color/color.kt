package org.polyfrost.elementa.state.v2.color

import org.polyfrost.elementa.constraints.ColorConstraint
import org.polyfrost.elementa.dsl.basicColorConstraint
import org.polyfrost.elementa.state.v2.State
import java.awt.Color

fun State<Color>.toConstraint() = basicColorConstraint { get() }

val State<Color>.constraint: ColorConstraint
    get() = toConstraint()
