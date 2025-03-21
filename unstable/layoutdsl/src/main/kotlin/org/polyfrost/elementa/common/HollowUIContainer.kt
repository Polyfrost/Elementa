package org.polyfrost.elementa.common

import org.polyfrost.elementa.components.UIContainer

/**
 * A UIContainer that does not return true for [isPointInside] unless
 * any of the child are hovered
 */
open class HollowUIContainer : UIContainer() {

    override fun isPointInside(x: Float, y: Float): Boolean {
        return children.any {
            it.isPointInside(x, y)
        }
    }
}