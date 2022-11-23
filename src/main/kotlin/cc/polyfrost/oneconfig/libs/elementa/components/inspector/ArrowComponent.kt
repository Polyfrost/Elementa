package cc.polyfrost.oneconfig.libs.elementa.components.inspector

import cc.polyfrost.oneconfig.libs.elementa.components.TreeArrowComponent
import cc.polyfrost.oneconfig.libs.elementa.components.UIImage
import cc.polyfrost.oneconfig.libs.elementa.constraints.CenterConstraint
import cc.polyfrost.oneconfig.libs.elementa.dsl.childOf
import cc.polyfrost.oneconfig.libs.elementa.dsl.constrain
import cc.polyfrost.oneconfig.libs.elementa.dsl.pixels
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack

class ArrowComponent(private val empty: Boolean) : TreeArrowComponent() {
    private val closedIcon = UIImage.ofResourceCached("/textures/inspector/square_plus.png").constrain {
        width = 7.pixels
        height = 7.pixels
        x = CenterConstraint()
        y = CenterConstraint()
    }
    private val openIcon = UIImage.ofResourceCached("/textures/inspector/square_minus.png").constrain {
        width = 7.pixels
        height = 7.pixels
        x = CenterConstraint()
        y = CenterConstraint()
    }

    init {
        constrain {
            width = 10.pixels
            height = 10.pixels
        }

        if (!empty)
            closedIcon childOf this
    }

    override fun open() {
        if (!empty)
            replaceChild(openIcon, closedIcon)
    }

    override fun close() {
        if (!empty)
            replaceChild(closedIcon, openIcon)
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDraw(matrixStack)
        super.draw(matrixStack)
    }
}