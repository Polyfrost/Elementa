package org.polyfrost.elementa.transitions

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.XConstraint
import org.polyfrost.elementa.constraints.YConstraint
import org.polyfrost.elementa.constraints.animation.AnimatingConstraints
import org.polyfrost.elementa.constraints.animation.Animations
import org.polyfrost.elementa.dsl.minus
import org.polyfrost.elementa.dsl.pixels
import org.polyfrost.elementa.dsl.plus

/**
 * Slides a component towards a certain direction by animations its
 * x or y constraint. Can optionally restore the original constraint
 * when finished.
 */
object SlideToTransition {
    class Left @JvmOverloads constructor(
        private val time: Float = 1f,
        private val animationType: Animations = Animations.OUT_EXP,
        private val restoreConstraints: Boolean = false
    ) : Transition() {
        private val xConstraints = mutableMapOf<UIComponent, XConstraint>()

        override fun beforeTransition(component: UIComponent) {
            xConstraints[component] = component.constraints.x
        }

        override fun doTransition(component: UIComponent, constraints: AnimatingConstraints) {
            constraints.setXAnimation(animationType, time, xConstraints[component]!! - component.getWidth().pixels())
        }

        override fun afterTransition(component: UIComponent) {
            if (restoreConstraints)
                component.setX(xConstraints[component]!!)
            xConstraints.remove(component)
        }
    }

    class Top @JvmOverloads constructor(
        private val time: Float = 1f,
        private val animationType: Animations = Animations.OUT_EXP,
        private val restoreConstraints: Boolean = false
    ) : Transition() {
        private val yConstraints = mutableMapOf<UIComponent, YConstraint>()

        override fun beforeTransition(component: UIComponent) {
            yConstraints[component] = component.constraints.y
        }

        override fun doTransition(component: UIComponent, constraints: AnimatingConstraints) {
            constraints.setYAnimation(animationType, time, yConstraints[component]!! - component.getHeight().pixels())
        }

        override fun afterTransition(component: UIComponent) {
            if (restoreConstraints)
                component.setY(yConstraints[component]!!)
            yConstraints.remove(component)
        }
    }

    class Right @JvmOverloads constructor(
        private val time: Float = 1f,
        private val animationType: Animations = Animations.OUT_EXP,
        private val restoreConstraints: Boolean = false
    ) : Transition() {
        private val xConstraints = mutableMapOf<UIComponent, XConstraint>()

        override fun beforeTransition(component: UIComponent) {
            xConstraints[component] = component.constraints.x
        }

        override fun doTransition(component: UIComponent, constraints: AnimatingConstraints) {
            constraints.setXAnimation(animationType, time, xConstraints[component]!! + component.getWidth().pixels())
        }

        override fun afterTransition(component: UIComponent) {
            if (restoreConstraints)
                component.setX(xConstraints[component]!!)
            xConstraints.remove(component)
        }
    }

    class Bottom @JvmOverloads constructor(
        private val time: Float = 1f,
        private val animationType: Animations = Animations.OUT_EXP,
        private val restoreConstraints: Boolean = false
    ) : Transition() {
        private val yConstraints = mutableMapOf<UIComponent, YConstraint>()

        override fun beforeTransition(component: UIComponent) {
            yConstraints[component] = component.constraints.y
        }

        override fun doTransition(component: UIComponent, constraints: AnimatingConstraints) {
            constraints.setYAnimation(animationType, time, yConstraints[component]!! + component.getHeight().pixels())
        }

        override fun afterTransition(component: UIComponent) {
            if (restoreConstraints)
                component.setY(yConstraints[component]!!)
            yConstraints.remove(component)
        }
    }
}

