package org.polyfrost.elementa.layoutdsl

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ColorConstraint
import org.polyfrost.elementa.constraints.animation.Animations
import org.polyfrost.elementa.dsl.animate
import org.polyfrost.elementa.dsl.toConstraint
import org.polyfrost.elementa.state.BasicState
import org.polyfrost.elementa.state.State
import org.polyfrost.elementa.state.toConstraint
import org.polyfrost.elementa.common.onSetValueAndNow
import org.polyfrost.elementa.state.v2.color.toConstraint
import org.polyfrost.elementa.state.v2.toV2
import org.polyfrost.elementa.util.hasWindow
import java.awt.Color
import org.polyfrost.elementa.state.v2.State as StateV2

fun Modifier.color(color: Color) = this then BasicColorModifier { color.toConstraint() }

@Deprecated("Using StateV1 is discouraged, use StateV2 instead")
fun Modifier.color(color: State<Color>) = this then BasicColorModifier { color.toConstraint() }

fun Modifier.color(color: StateV2<Color>) = this then BasicColorModifier { color.toConstraint() }

fun Modifier.hoverColor(color: Color, duration: Float = 0f) = hoverColor(BasicState(color), duration)

@Deprecated("Using StateV1 is discouraged, use StateV2 instead")
fun Modifier.hoverColor(color: State<Color>, duration: Float = 0f) = whenHovered(if (duration == 0f) Modifier.color(color) else Modifier.animateColor(color, duration))

fun Modifier.hoverColor(color: StateV2<Color>, duration: Float = 0f) = whenHovered(if (duration == 0f) Modifier.color(color) else Modifier.animateColor(color, duration))

fun Modifier.animateColor(color: Color, duration: Float = .3f) = animateColor(BasicState(color), duration)

fun Modifier.animateColor(color: State<Color>, duration: Float = .3f) = animateColor(color.toV2(), duration)

fun Modifier.animateColor(color: StateV2<Color>, duration: Float = .3f) = this then AnimateColorModifier(color, duration)

private class AnimateColorModifier(private val colorState: StateV2<Color>, private val duration: Float) : Modifier {
    override fun applyToComponent(component: UIComponent): () -> Unit {
        val oldColor = component.constraints.color

        fun animate(color: ColorConstraint) {
            if (component.hasWindow) {
                component.animate {
                    setColorAnimation(Animations.OUT_EXP, duration, color)
                }
            } else {
                component.setColor(color)
            }
        }

        val removeListenerCallback = colorState.onSetValueAndNow(component) {
            animate(it.toConstraint())
        }

        return {
            removeListenerCallback()
            animate(oldColor)
        }
    }
}
