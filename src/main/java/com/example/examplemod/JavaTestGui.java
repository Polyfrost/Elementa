package com.example.examplemod;

import org.polyfrost.elementa.ElementaVersion;
import org.polyfrost.elementa.UIComponent;
import org.polyfrost.elementa.WindowScreen;
import org.polyfrost.elementa.components.UIBlock;
import org.polyfrost.elementa.constraints.CenterConstraint;
import org.polyfrost.elementa.constraints.ChildBasedSizeConstraint;
import org.polyfrost.elementa.constraints.PixelConstraint;
import org.polyfrost.elementa.constraints.animation.AnimatingConstraints;
import org.polyfrost.elementa.constraints.animation.Animations;
import org.polyfrost.elementa.effects.ScissorEffect;

public class JavaTestGui extends WindowScreen {
    UIComponent box = new UIBlock()
        .setX(new CenterConstraint())
        .setY(new PixelConstraint(10f))
        .setWidth(new PixelConstraint(10f))
        .setHeight(new PixelConstraint(36f))
        .setChildOf(getWindow())
        .enableEffect(new ScissorEffect());

    public JavaTestGui() {
        super(ElementaVersion.V2);
        box.onMouseEnterRunnable(() -> {
            // Animate, set color, etc.
            AnimatingConstraints anim = box.makeAnimation();
            anim.setWidthAnimation(Animations.OUT_EXP, 0.5f, new ChildBasedSizeConstraint(2f));
            anim.onCompleteRunnable(() -> {
                // Trigger new animation or anything.
            });
            box.animateTo(anim);
        });
    }
}
