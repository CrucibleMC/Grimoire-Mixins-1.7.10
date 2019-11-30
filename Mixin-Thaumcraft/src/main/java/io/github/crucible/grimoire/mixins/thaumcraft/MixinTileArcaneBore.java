package io.github.crucible.grimoire.mixins.thaumcraft;

import com.gamerforea.eventhelper.util.EventUtils;
import io.github.crucible.grimoire.forge.core.ITileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.tiles.TileArcaneBore;

@Mixin(value = TileArcaneBore.class, remap = false)
public abstract class MixinTileArcaneBore implements ITileEntity {

    @Shadow
    int digX;
    @Shadow
    int digY;
    @Shadow
    int digZ;

    @Inject(method = "dig", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfo ci) {
        if (EventUtils.cantBreak(getFakePlayer(), digX, digY, digZ)) ci.cancel();
    }
}
