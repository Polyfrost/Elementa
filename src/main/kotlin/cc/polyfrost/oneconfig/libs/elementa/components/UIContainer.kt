package cc.polyfrost.oneconfig.libs.elementa.components

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack

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