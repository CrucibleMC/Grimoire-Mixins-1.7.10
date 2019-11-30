package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

@Mixin(value = ToolCommons.class, remap = false)
public abstract class MixinToolCommons {

    @Inject(method = "removeBlockWithDrops(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;IIIIIILnet/minecraft/block/Block;[Lnet/minecraft/block/material/Material;ZIFZZ)V", at = @At(value = "HEAD"), cancellable = true)
    private static void removeBlockWithDropsCheck(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int bx, int by, int bz, Block block, Material[] materialsListing, boolean silk, int fortune, float blockHardness, boolean dispose, boolean particles, CallbackInfo ci) {
        if (world.blockExists(x, y, z) && EventUtils.cantBreak(player, x, y, z)) ci.cancel();
    }

}