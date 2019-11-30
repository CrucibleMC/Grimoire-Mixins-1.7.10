package io.github.crucible.grimoire.mixins.cofh;

import cofh.core.network.PacketCoFHBase;
import cofh.thermalexpansion.block.cache.TileCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {TileCache.class}, remap = false)
public class MixinThermalTile {

    @Inject(method = "handleTilePacket", at = @At("INVOKE"), cancellable = true)
    private void handleTilePacket(PacketCoFHBase payload, boolean isServer, CallbackInfo ci) {
        ci.cancel();
    }
}
