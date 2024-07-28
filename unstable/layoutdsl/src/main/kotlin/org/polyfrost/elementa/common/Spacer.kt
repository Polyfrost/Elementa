package org.polyfrost.elementa.common

import org.polyfrost.elementa.constraints.HeightConstraint
import org.polyfrost.elementa.constraints.SiblingConstraint
import org.polyfrost.elementa.constraints.WidthConstraint
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.dsl.pixels

/**
 * A simple UIContainer where you can specify [width], [height], or both.
 *
 * If only [width] is specified, X-axis will be constrained to [SiblingConstraint].
 *
 * If only [height] is specified, Y-axis will be constrained to [SiblingConstraint].
 */
class Spacer(width: WidthConstraint = 0.pixels, height: HeightConstraint = 0.pixels) : HollowUIContainer() {
    constructor(width: Float, _desc: Int = 0) : this(width = width.pixels) { setX(SiblingConstraint()) }
    constructor(height: Float, _desc: Short = 0) : this(height = height.pixels) { setY(SiblingConstraint()) }
    constructor(width: Float, height: Float) : this(width = width.pixels, height = height.pixels)

    init {
        constrain {
            this.width = width
            this.height = height
        }
    }
}
