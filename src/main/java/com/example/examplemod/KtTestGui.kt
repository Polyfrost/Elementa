package com.example.examplemod

import cc.polyfrost.oneconfig.libs.elementa.ElementaVersion
import cc.polyfrost.oneconfig.libs.elementa.WindowScreen
import cc.polyfrost.oneconfig.libs.elementa.components.*
import cc.polyfrost.oneconfig.libs.elementa.components.input.UIMultilineTextInput
import cc.polyfrost.oneconfig.libs.elementa.components.inspector.Inspector
import cc.polyfrost.oneconfig.libs.elementa.constraints.*
import cc.polyfrost.oneconfig.libs.elementa.constraints.animation.Animations
import cc.polyfrost.oneconfig.libs.elementa.dsl.*
import cc.polyfrost.oneconfig.libs.elementa.effects.OutlineEffect
import cc.polyfrost.oneconfig.libs.elementa.effects.ScissorEffect
import cc.polyfrost.oneconfig.libs.elementa.font.DefaultFonts
import cc.polyfrost.oneconfig.libs.elementa.font.ElementaFonts
import cc.polyfrost.oneconfig.libs.elementa.utils.withAlpha
import java.awt.Color

class KtTestGui : WindowScreen(ElementaVersion.V2) {
    private val myTextBox = UIBlock(Color(0, 0, 0, 255))

    init {
        val container = UIContainer().constrain {
            x = RelativeConstraint(.25f)
            y = RelativeConstraint(.25f)
            width = RelativeConstraint(.5f)
            height = RelativeConstraint(.5f)
        } childOf window
        for (i in 50..500) {
            if (i % 15 != 0) continue
            UIBlock(Color.RED).constrain {
                x = CramSiblingConstraint(10 / 3f)
                y = CramSiblingConstraint(10f / 3f)
                width = 5.pixels()
                height = 5.pixels()
            } childOf container effect OutlineEffect(Color.BLUE, 1f);
        }
    }
}