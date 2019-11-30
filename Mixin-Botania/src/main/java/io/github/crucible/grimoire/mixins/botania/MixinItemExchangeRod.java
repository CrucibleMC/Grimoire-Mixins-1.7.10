package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.rod.ItemExchangeRod;

@Mixin(value = ItemExchangeRod.class, remap = false)
public abstract class MixinItemExchangeRod {

    @Inject(method = "exchange", at = @At("HEAD"), cancellable = true)
    private void checkPermission(World world, EntityPlayer player, int x, int y, int z, ItemStack stack, Block blockToSet, int metaToSet, CallbackInfoReturnable<Boolean> ci) {
        if (world.blockExists(x, y, z) && EventUtils.cantBreak(player, x, y, z)) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }
}
