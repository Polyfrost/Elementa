package cc.polyfrost.oneconfig.libs.elementa.components

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.constraints.PositionConstraint
import cc.polyfrost.oneconfig.libs.elementa.dsl.pixels
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack

/**
 * "Component" with no width/height and therefore no visible rendering.
 *
 * Used primarily for [UIShape]
 */
class UIPoint(
    val x: PositionConstraint,
    val y: PositionConstraint
) : UIComponent() {
    val relativeX: Float
        get() = constraints.getX()

    val relativeY: Float
        get() = constraints.getY()

    val absoluteX: Float
        get() = getLeft()

    val absoluteY: Float
        get() = getTop()

    val point: Pair<Float, Float>
        get() = relativeX to relativeY

    val absolutePoint: Pair<Float, Float>
        get() = absoluteX to absoluteY

    init {
        setX(x)
        setY(y)
    }

    constructor(x: Number, y: Number) : this(x.pixels(), y.pixels())

    constructor(point: Pair<Number, Number>) : this(point.first.pixels(), point.second.pixels())

    fun withX(x: PositionConstraint) = UIPoint(x, y)

    fun withX(x: Number) = UIPoint(x.pixels(), y)

    fun withY(y: PositionConstraint) = UIPoint(x, y)

    fun withY(y: Number) = UIPoint(x, y.pixels())

    override fun draw(matrixStack: UMatrixStack) {
        beforeDraw(matrixStack)
        super.draw(matrixStack)
    }
}