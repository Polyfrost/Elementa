package org.polyfrost.elementa.components.inspector

import org.polyfrost.elementa.components.TreeArrowComponent
import org.polyfrost.elementa.components.UIImage
import org.polyfrost.elementa.constraints.CenterConstraint
import org.polyfrost.elementa.dsl.childOf
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.dsl.pixels
import org.polyfrost.universal.UMatrixStack

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