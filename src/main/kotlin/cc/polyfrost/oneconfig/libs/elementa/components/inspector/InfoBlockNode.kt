package cc.polyfrost.oneconfig.libs.elementa.components.inspector

import cc.polyfrost.oneconfig.libs.elementa.UIComponent
import cc.polyfrost.oneconfig.libs.elementa.components.TreeNode
import cc.polyfrost.oneconfig.libs.elementa.components.UIContainer
import cc.polyfrost.oneconfig.libs.elementa.components.UIText
import cc.polyfrost.oneconfig.libs.elementa.constraints.*
import cc.polyfrost.oneconfig.libs.elementa.constraints.animation.AnimationComponent
import cc.polyfrost.oneconfig.libs.elementa.dsl.childOf
import cc.polyfrost.oneconfig.libs.elementa.dsl.constrain
import cc.polyfrost.oneconfig.libs.elementa.dsl.pixels
import cc.polyfrost.oneconfig.libs.elementa.dsl.plus
import java.awt.Color

class InfoBlockNode<T>(private val constraint: SuperConstraint<T>, private val name: String? = null) : TreeNode() {
    override fun getArrowComponent() = ArrowComponent(!constraintHasChildren(constraint))

    override fun getPrimaryComponent() = object : UIContainer() {
        val stringHolder: UIComponent

        init {
            val name = constraint.javaClass.simpleName.let {
                if (name == null) it else "$name: $it"
            }
            UIText(name).constrain {
                x = SiblingConstraint()
            } childOf this

            val properties = when (constraint) {
                is AlphaAspectColorConstraint -> listOf(constraint::color, constraint::alpha)
                is AspectConstraint -> listOf(constraint::value)
                is ChildBasedSizeConstraint -> listOf(constraint::padding)
                is ConstantColorConstraint -> listOf(constraint::color)
                is CramSiblingConstraint -> listOf(constraint::padding)
                is PixelConstraint -> listOf(
                    constraint::value,
                    constraint::alignOpposite,
                    constraint::alignOutside
                )
                is RainbowColorConstraint -> listOf(constraint::alpha, constraint::speed)
                is RelativeConstraint -> listOf(constraint::value)
                is ScaledTextConstraint -> listOf(constraint::scale)
                is SiblingConstraint -> listOf(constraint::padding, constraint::alignOpposite)
                else -> listOf()
            }

            fun toString(o: Any) = when (o) {
                is Color -> "Color(${o.red}, ${o.green}, ${o.blue}, ${o.alpha})"
                is Double, is Float -> "%.2f".format(o)
                else -> o.toString()
            }

            stringHolder = UIContainer().constrain {
                x = 13.pixels()
                y = SiblingConstraint()
                width = ChildBasedMaxSizeConstraint()
                height = ChildBasedSizeConstraint()
            } childOf this

            if (constraint is AnimationComponent<*>) {
                createStringComponent("§7Strategy: ${constraint.strategy}§r")
                val percentComplete = constraint.elapsedFrames.toFloat() / (constraint.totalFrames + constraint.delayFrames)
                createStringComponent("§7Completion Percentage: ${Inspector.percentFormat.format(percentComplete)}§r")
                createStringComponent("§7Paused: ${constraint.animationPaused}§r")
            }

            properties.forEach {
                createStringComponent("§7${it.name}: ${toString(it.get())}§r")
            }
        }

        fun createStringComponent(text: String) {
            UIText(text).constrain {
                y = SiblingConstraint()
            } childOf stringHolder
        }

        override fun animationFrame() {
            super.animationFrame()

            if (constraint is AnimationComponent<*>) {
                val strings = stringHolder.childrenOfType<UIText>()
                val percentComplete = constraint.elapsedFrames.toFloat() / (constraint.totalFrames + constraint.delayFrames)
                strings[1].setText("§7Completion Percentage: ${Inspector.percentFormat.format(percentComplete)}§r")
                strings[2].setText("§7Paused: ${constraint.animationPaused}§r")
            }
        }
    }.constrain {
        x = SiblingConstraint()
        y = 2.pixels()
        width = ChildBasedMaxSizeConstraint() + 10.pixels()
        height = ChildBasedSizeConstraint() + 5.pixels()
    }

    companion object {
        private fun constraintHasChildren(constraint: SuperConstraint<*>) = when (constraint) {
            is AdditiveConstraint,
            is CoerceAtMostConstraint,
            is CoerceAtLeastConstraint,
            is SubtractiveConstraint -> true
            is AnimationComponent<*> -> true
            else -> false
        }
    }
}
