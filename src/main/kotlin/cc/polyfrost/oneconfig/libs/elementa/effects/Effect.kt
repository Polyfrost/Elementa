package cc.polyfrost.oneconfig.libs.elementa.effects

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack

/**
 * Basic interface all effects need to follow.
 *
 * This is where you can affect any drawing done.
 */
abstract class Effect {
    protected lateinit var boundComponent: UIComponent

    fun bindComponent(component: UIComponent) {
        if (::boundComponent.isInitialized && boundComponent != component)
            throw IllegalStateException("Attempt to bind component of a ${this::class.simpleName} " +
                "which already has a bound component")
        boundComponent = component
    }
    /**
     * Called once inside of the component's afterInitialization function
     */
    open fun setup() {}

    /**
     * Called in the component's animationFrame function
     */
    open fun animationFrame() {}

    /**
     * Set up all drawing, turn on shaders, etc.
     */
    open fun beforeDraw(matrixStack: UMatrixStack) {}

    /**
     * Called after this component draws but before it's children are drawn.
     */
    open fun beforeChildrenDraw(matrixStack: UMatrixStack) {}

    /**
     * Clean up all of this feature's GL states, etc.
     */
    open fun afterDraw(matrixStack: UMatrixStack) {}


    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("beforeDraw(matrixStack)")
    )
    open fun beforeDraw() = beforeDraw(UMatrixStack.Compat.get())

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("beforeChildrenDraw(matrixStack)")
    )
    open fun beforeChildrenDraw() = beforeChildrenDraw(UMatrixStack.Compat.get())

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("afterDraw(matrixStack)")
    )
    open fun afterDraw() = afterDraw(UMatrixStack.Compat.get())

    @Suppress("DEPRECATION")
    fun beforeDrawCompat(matrixStack: UMatrixStack) = UMatrixStack.Compat.runLegacyMethod(matrixStack) { beforeDraw() }
    @Suppress("DEPRECATION")
    fun beforeChildrenDrawCompat(matrixStack: UMatrixStack) = UMatrixStack.Compat.runLegacyMethod(matrixStack) { beforeChildrenDraw() }
    @Suppress("DEPRECATION")
    fun afterDrawCompat(matrixStack: UMatrixStack) = UMatrixStack.Compat.runLegacyMethod(matrixStack) { afterDraw() }
}
