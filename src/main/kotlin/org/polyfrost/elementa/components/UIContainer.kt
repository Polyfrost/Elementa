package org.polyfrost.elementa.components

import org.polyfrost.elementa.UIComponent
import org.polyfrost.universal.UMatrixStack

/**
 * Bare-bones component that does no rendering and simply offers a bounding box.
 */
open class UIContainer : UIComponent() {
    override fun draw(matrixStack: UMatrixStack) {
        // This is necessary because if it isn't here, effects will never be applied.
        beforeDrawCompat(matrixStack)

        // no-op

        super.draw(matrixStack)
    }
}