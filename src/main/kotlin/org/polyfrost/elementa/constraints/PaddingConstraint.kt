package org.polyfrost.elementa.constraints

import org.polyfrost.elementa.UIComponent

interface PaddingConstraint {

    fun getVerticalPadding(component: UIComponent): Float

    fun getHorizontalPadding(component: UIComponent) : Float
}