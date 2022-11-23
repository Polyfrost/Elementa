package com.example.examplemod;

import cc.polyfrost.oneconfig.libs.elementa.ElementaVersion;
import cc.polyfrost.oneconfig.libs.elementa.UIComponent;
import cc.polyfrost.oneconfig.libs.elementa.WindowScreen;
import cc.polyfrost.oneconfig.libs.elementa.components.UIBlock;
import cc.polyfrost.oneconfig.libs.elementa.constraints.CenterConstraint;
import cc.polyfrost.oneconfig.libs.elementa.constraints.ChildBasedSizeConstraint;
import cc.polyfrost.oneconfig.libs.elementa.constraints.PixelConstraint;
import cc.polyfrost.oneconfig.libs.elementa.constraints.animation.AnimatingConstraints;
import cc.polyfrost.oneconfig.libs.elementa.constraints.animation.Animations;
import cc.polyfrost.oneconfig.libs.elementa.effects.ScissorEffect;

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
