package org.polyfrost.elementa.impl;

import org.polyfrost.universal.UMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.shader.Framebuffer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@SuppressWarnings("unused") // instantiated via reflection from Platform.Companion
public class PlatformImpl implements Platform {

    @Override
    public int getMcVersion() {
        //#if MC==11802
        //$$ return 11802;
        //#elseif MC==11701
        //$$ return 11701;
        //#elseif MC==11605
        //$$ return 11605;
        //#elseif MC==11502
        //$$ return 11502;
        //#elseif MC==11202
        return 11202;
        //#elseif MC==10809
        //$$ return 10809;
        //#endif
    }

    @Nullable
    @Override
    public Object getCurrentScreen() {
        return Minecraft.getMinecraft().currentScreen;
    }

    @Override
    public void setCurrentScreen(@Nullable Object screen) {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen) screen);
    }

    @Override
    public void enableStencil() {
        //#if MC<=11202 && FORGE==1
        Framebuffer framebuffer = UMinecraft.getMinecraft().getFramebuffer();
        if (!framebuffer.isStencilEnabled()) {
            framebuffer.enableStencil();
        }
        //#elseif MC>=11700 && FORGE==1
        //$$ RenderTarget framebuffer = UMinecraft.getMinecraft().getMainRenderTarget();
        //$$ if (!framebuffer.isStencilEnabled()) {
        //$$     framebuffer.enableStencil();
        //$$ }
        //#elseif MC>=11600 && FORGE==1
        //$$ Framebuffer framebuffer = UMinecraft.getMinecraft().getFramebuffer();
        //$$ if (!framebuffer.isStencilEnabled()) {
        //$$     framebuffer.enableStencil();
        //$$ }
        //#endif
    }

    @Override
    public boolean isCallingFromMinecraftThread() {
        //#if MC>=11400
        //$$ return Minecraft.getInstance().isOnExecutionThread();
        //#else
        return Minecraft.getMinecraft().isCallingFromMinecraftThread();
        //#endif
    }
}
