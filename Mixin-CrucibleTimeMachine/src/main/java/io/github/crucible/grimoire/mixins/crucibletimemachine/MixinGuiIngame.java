package io.github.crucible.grimoire.mixins.crucibletimemachine;

import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiIngame.class)
public abstract class MixinGuiIngame {

    @Inject(method = "renderBossHealth",at = @At("HEAD"), cancellable = true)
    private void renderBossHealth(CallbackInfo ci){

        boolean ready = false;

        try {
            Class<?> clazz = Class.forName("io.github.crucible.timemachine.bossbar.BossBarAPI");

            ready = (boolean) clazz.getDeclaredField("READY").get(null);

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignored) {}

        if(ready){
            ci.cancel();
        }

    }

}
