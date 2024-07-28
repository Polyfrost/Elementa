package com.example.examplemod

import org.polyfrost.elementa.constraints.SiblingConstraint
import org.polyfrost.elementa.ElementaVersion
import org.polyfrost.elementa.components.ScrollComponent
import org.polyfrost.elementa.components.UIBlock
import org.polyfrost.elementa.WindowScreen
import org.polyfrost.elementa.components.UIText
import org.polyfrost.elementa.constraints.CenterConstraint
import org.polyfrost.elementa.constraints.animation.Animations
import org.polyfrost.elementa.impl.Platform.Companion.platform
import org.polyfrost.universal.UScreen
import org.polyfrost.elementa.dsl.*
import org.polyfrost.elementa.dsl.toConstraint
import java.awt.Color

/**
 * List of buttons to open a specific example gui.
 * See ExampleGui (singular) for a well-commented example gui.
 */
class ExamplesGui : WindowScreen(ElementaVersion.V2) {
    private val container by ScrollComponent().constrain {
        y = 3.pixels()
        width = 100.percent()
        height = 100.percent() - (3 * 2).pixels()
    } childOf window

    init {
        for ((name, action) in examples) {
            val button = UIBlock().constrain {
                x = CenterConstraint()
                y = SiblingConstraint(padding = 3f)
                width = 200.pixels()
                height = 20.pixels()
                color = Color(255, 255, 255, 102).toConstraint()
            }.onMouseEnter {
                animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, Color(255, 255, 255, 150).toConstraint())
                }
            }.onMouseLeave {
                animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, Color(255, 255, 255, 102).toConstraint())
                }
            }.onMouseClick {
                try {
                    platform.currentScreen = action()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } childOf container

            UIText(name).constrain {
                x = CenterConstraint()
                y = CenterConstraint()
            } childOf button
        }
    }

    companion object {
        val examples = mutableMapOf<String, () -> UScreen>(
            "ExampleGui" to ::ExampleGui,
            "ComponentsGui" to ::ComponentsGui,
        )
    }
}
