package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.botania.common.item.rod.ItemTerraformRod;

@Mixin(value = ItemTerraformRod.class, remap = false)
public abstract class MixinItemTerraformRod {

    @Redirect(method = "terraform", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147449_b(IIILnet/minecraft/block/Block;)Z"))
    private boolean setBlockProxy(World world, int x, int y, int z, Block block, ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (EventUtils.cantBreak(par3EntityPlayer, x, y, z)) return false;
        else return world.setBlock(x, y, z, block);
    }

}
