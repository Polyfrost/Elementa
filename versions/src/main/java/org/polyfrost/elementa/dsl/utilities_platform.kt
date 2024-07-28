@file:JvmName("UtilitiesKt_platform")
package org.polyfrost.elementa.dsl

import org.polyfrost.elementa.font.DefaultFonts
import org.polyfrost.elementa.font.FontProvider
import org.polyfrost.universal.wrappers.message.UTextComponent
import net.minecraft.util.text.ITextComponent

fun ITextComponent.width(textScale: Float = 1f, fontProvider: FontProvider = DefaultFonts.VANILLA_FONT_RENDERER) =
    UTextComponent(this).formattedText.width(textScale, fontProvider)
