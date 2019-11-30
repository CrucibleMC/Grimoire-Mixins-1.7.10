package io.github.crucible.grimoire.mixins.mekanism;

import com.gamerforea.eventhelper.util.EventUtils;
import mekanism.common.entity.EntityFlame;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFlame.class, remap = false)
public abstract class MixinFlamethrower {

    @Shadow
    public Entity owner;

    @Inject(method = "burn", at = @At("HEAD"), cancellable = true)
    private void checkPermission(Entity entity, CallbackInfo ci) {
        if (EventUtils.cantDamage(owner, entity)) {
            ci.cancel();
        }
    }

}