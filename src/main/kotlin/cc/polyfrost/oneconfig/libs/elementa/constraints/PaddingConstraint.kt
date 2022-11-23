package cc.polyfrost.oneconfig.libs.elementa.constraints

import cc.polyfrost.oneconfig.libs.elementa.UIComponent

interface PaddingConstraint {

    fun getVerticalPadding(component: UIComponent): Float

    fun getHorizontalPadding(component: UIComponent) : Float
}