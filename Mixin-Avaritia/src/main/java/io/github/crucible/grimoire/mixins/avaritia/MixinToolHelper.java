package io.github.crucible.grimoire.mixins.avaritia;

import com.gamerforea.eventhelper.util.EventUtils;
import fox.spiteful.avaritia.items.tools.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ToolHelper.class, remap = false)
public abstract class MixinToolHelper {

    @Inject(method = "removeBlockWithDrops", at = @At("HEAD"), cancellable = true)
    private static void removeBlockWithDropsCheck(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, Block block, Material[] materialsListing, boolean silk, int fortune, float blockHardness, boolean dispose, CallbackInfo ci) {
        if (EventUtils.cantBreak(player, x, y, z)) ci.cancel();
    }

}