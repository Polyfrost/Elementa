package org.polyfrost.elementa.dsl

import org.polyfrost.elementa.font.DefaultFonts
import org.polyfrost.elementa.font.FontProvider
import org.polyfrost.universal.UGraphics
import org.polyfrost.elementa.constraints.*
import java.awt.Color

fun Char.width(textScale: Float = 1f) = UGraphics.getCharWidth(this) * textScale

fun String.width(textScale: Float = 1f, fontProvider: FontProvider = DefaultFonts.VANILLA_FONT_RENDERER) =
    fontProvider.getStringWidth(this, 10f) * textScale

@JvmOverloads
fun Number.pixels(alignOpposite: Boolean = false, alignOutside: Boolean = false): PixelConstraint =
    PixelConstraint(this.toFloat(), alignOpposite, alignOutside)

val Number.pixels: PixelConstraint
    get() = pixels(alignOpposite = false, alignOutside = false)

// For 1 pixel
@JvmOverloads
fun Number.pixel(alignOpposite: Boolean = false, alignOutside: Boolean = false) = pixels(alignOpposite, alignOutside)
val Number.pixel: PixelConstraint
    get() = pixels

fun Number.percent() = RelativeConstraint(this.toFloat() / 100f)
val Number.percent: RelativeConstraint
    get() = this.percent()

fun Number.percentOfWindow() = RelativeWindowConstraint(this.toFloat() / 100f)
val Number.percentOfWindow: RelativeWindowConstraint
    get() = this.percentOfWindow()

fun SuperConstraint<Float>.floor() = RoundingConstraint(this, org.polyfrost.elementa.constraints.RoundingConstraint.Mode.Floor)
fun SuperConstraint<Float>.ceil() = RoundingConstraint(this, org.polyfrost.elementa.constraints.RoundingConstraint.Mode.Ceil)
fun SuperConstraint<Float>.round() = RoundingConstraint(this, org.polyfrost.elementa.constraints.RoundingConstraint.Mode.Round)

fun Color.toConstraint() = ConstantColorConstraint(this)
val Color.constraint: ConstantColorConstraint
    get() = this.toConstraint()

operator fun Color.component1() = red
operator fun Color.component2() = green
operator fun Color.component3() = blue
operator fun Color.component4() = alpha
