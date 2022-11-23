@file:JvmName("UtilitiesKt_platform")
package cc.polyfrost.oneconfig.libs.elementa.dsl

import cc.polyfrost.oneconfig.libs.elementa.font.DefaultFonts
import cc.polyfrost.oneconfig.libs.elementa.font.FontProvider
import cc.polyfrost.oneconfig.libs.universal.wrappers.message.UTextComponent
import net.minecraft.util.text.ITextComponent

fun ITextComponent.width(textScale: Float = 1f, fontProvider: FontProvider = DefaultFonts.VANILLA_FONT_RENDERER) =
    UTextComponent(this).formattedText.width(textScale, fontProvider)
