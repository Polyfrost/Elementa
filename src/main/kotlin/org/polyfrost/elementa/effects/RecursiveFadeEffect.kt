package org.polyfrost.elementa.effects

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.ColorConstraint
import org.polyfrost.elementa.constraints.ConstraintType
import org.polyfrost.elementa.constraints.resolution.ConstraintVisitor
import org.polyfrost.elementa.dsl.constrain
import org.polyfrost.elementa.state.BasicState
import org.polyfrost.elementa.state.MappedState
import org.polyfrost.elementa.state.State
import org.polyfrost.elementa.utils.withAlpha
import java.awt.Color
import kotlin.math.roundToInt

/**
 * Fades a component's color as well as all of its children.
 */
class RecursiveFadeEffect constructor(
    isOverridden: State<Boolean> = BasicState(false),
    overriddenAlphaPercentage: State<Float> = BasicState(1f)
) : Effect() {
    private val isOverridden: MappedState<Boolean, Boolean> = isOverridden.map { it }
    private val overriddenAlphaPercentage: MappedState<Float, Float> = overriddenAlphaPercentage.map { it }

    fun rebindIsOverridden(state: State<Boolean>) = apply {
        isOverridden.rebind(state)
    }

    fun rebindOverriddenAlphaPercentage(state: State<Float>) = apply {
        overriddenAlphaPercentage.rebind(state)
    }

    override fun setup() {
        recurseChildren(boundComponent) {
            it.constrain {
                color = OverridableAlphaColorConstraint(color, isOverridden, overriddenAlphaPercentage)
            }
        }
    }

    fun remove() {
        recurseChildren(boundComponent) {
            if (it.constraints.color is OverridableAlphaColorConstraint) {
                it.constrain {
                    color = (color as OverridableAlphaColorConstraint).originalConstraint
                }
            }
        }
    }

    private fun recurseChildren(component: UIComponent, action: (UIComponent) -> Unit) {
        action(component)
        component.children.forEach { recurseChildren(it, action) }
    }

    private class OverridableAlphaColorConstraint(
        val originalConstraint: ColorConstraint,
        private val isOverridden: State<Boolean>,
        private val overriddenAlphaPercentage: State<Float>
    ) : ColorConstraint {
        override var cachedValue: Color = Color.WHITE
        override var constrainTo: UIComponent? = null
        override var recalculate = true

        init {
            isOverridden.onSetValue {
                recalculate = true
            }

            overriddenAlphaPercentage.onSetValue {
                recalculate = true
            }
        }

        override fun animationFrame() {
            // We still want the original constraint's colour to recalculate while we are animating
            originalConstraint.animationFrame()
        }

        override fun getColorImpl(component: UIComponent): Color {
            val originalColor = originalConstraint.getColorImpl(component)
            val originalAlpha = originalColor.alpha

            if (isOverridden.get())
                return originalColor.withAlpha((originalAlpha * overriddenAlphaPercentage.get()).roundToInt().coerceIn(0, 255))
            return originalColor
        }

        override fun visitImpl(visitor: ConstraintVisitor, type: ConstraintType) {
            // no-op
        }
    }
}
