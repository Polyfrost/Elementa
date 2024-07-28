package org.polyfrost.elementa.layoutdsl

import org.polyfrost.elementa.components.UIContainer
import org.polyfrost.elementa.components.Window
import org.polyfrost.elementa.state.BasicState
import org.polyfrost.elementa.state.State
import org.polyfrost.universal.UMatrixStack

/**
 * Lazily initializes the inner scope by first only placing a [box] as described by [modifier] without any children and
 * only initializing the inner scope once that box has been rendered once.
 *
 * This should be a last reserve for initializing a large list of poorly optimized components, not a common shortcut to
 * "make it not lag". Properly profiling and fixing initialization performance issues should always be preferred.
 */
fun LayoutScope.lazyBox(modifier: Modifier = Modifier.fillParent(), block: LayoutScope.() -> Unit) {
    val initialized = BasicState(false)
    box(modifier) {
        if_(initialized, cache = false /** don't need it; once initialized, we are never going back */) {
            block()
        } `else` {
            LazyComponent(initialized)(Modifier.fillParent())
        }
    }
}

private class LazyComponent(private val initialized: State<Boolean>) : UIContainer() {
    override fun draw(matrixStack: UMatrixStack) {
        super.draw(matrixStack)

        Window.enqueueRenderOperation {
            initialized.set(true)
        }
    }
}
