package com.example.examplemod

import org.polyfrost.elementa.components.UIContainer
import org.polyfrost.elementa.constraints.CramSiblingConstraint
import org.polyfrost.elementa.constraints.RelativeConstraint
import org.polyfrost.elementa.dsl.pixels
import org.polyfrost.elementa.ElementaVersion
import org.polyfrost.elementa.components.UIBlock
import org.polyfrost.elementa.WindowScreen
import org.polyfrost.elementa.dsl.childOf
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.dsl.effect
import org.polyfrost.elementa.effects.OutlineEffect
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