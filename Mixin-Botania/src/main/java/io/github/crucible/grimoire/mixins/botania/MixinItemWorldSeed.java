package io.github.crucible.grimoire.mixins.botania;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.ItemWorldSeed;

@Mixin(value = ItemWorldSeed.class, remap = false)
public abstract class MixinItemWorldSeed {
    @Inject(method = "func_77659_a", at = @At("HEAD"), cancellable = true)
    private void checkPlayer(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> ci) {
        if (player instanceof FakePlayer) {
            ci.setReturnValue(stack);
            ci.cancel();
        }
    }
}
