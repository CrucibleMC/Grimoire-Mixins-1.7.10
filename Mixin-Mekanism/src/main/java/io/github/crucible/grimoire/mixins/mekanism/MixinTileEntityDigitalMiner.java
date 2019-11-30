package io.github.crucible.grimoire.mixins.mekanism;


import io.github.crucible.grimoire.forge.core.ITileEntity;
import mekanism.common.CommonProxy;
import mekanism.common.tile.TileEntityDigitalMiner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.ref.WeakReference;

@Mixin(value = TileEntityDigitalMiner.class, remap = false)
public abstract class MixinTileEntityDigitalMiner implements ITileEntity {

    @Redirect(method = "setReplace", at = @At(value = "INVOKE", target = "Lmekanism/common/CommonProxy;getDummyPlayer(Lnet/minecraft/world/WorldServer;DDD)Ljava/lang/ref/WeakReference;"))
    private WeakReference<EntityPlayer> getDummyPlayerProxy(CommonProxy proxy, WorldServer world, double x, double y, double z) {
        return new WeakReference<>(this.getFakePlayer());
    }
}
