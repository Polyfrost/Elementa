package org.polyfrost.elementa.dsl

import org.polyfrost.elementa.UIComponent
import org.polyfrost.elementa.constraints.animation.AnimatingConstraints

/**
 * Wrapper around [UIComponent.makeAnimation] and [UIComponent.animateTo],
 * providing a handy dandy DSL.
 */
inline fun <T : UIComponent> T.animate(animation: AnimatingConstraints.() -> Unit) = apply {
    val anim = this.makeAnimation()
    anim.animation()
    this.animateTo(anim)
}
