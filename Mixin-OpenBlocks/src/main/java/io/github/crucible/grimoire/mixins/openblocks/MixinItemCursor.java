package io.github.crucible.grimoire.mixins.openblocks;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import openblocks.common.item.ItemCursor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ItemCursor.class, remap = false)
public abstract class MixinItemCursor {

    @Shadow
    private static void clickBlock(World world, EntityPlayer player, int x, int y, int z, int side) {}

    /**
     * @author EverNife
     * @reason Proteger baus e interfaces que estejam em terreno de outros jogadores
     */
    @Overwrite
    public ItemStack func_77659_a(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            return itemStack;
        } else {
            NBTTagCompound tag = itemStack.getTagCompound();
            if (tag != null && tag.hasKey("x") && tag.hasKey("y") && tag.hasKey("z") && tag.hasKey("dimension")) {
                int x = tag.getInteger("x");
                int y = tag.getInteger("y");
                int z = tag.getInteger("z");
                int dimension = tag.getInteger("dimension");
                if (world.provider.dimensionId == dimension && world.blockExists(x, y, z)) {
                    if (!EventUtils.cantBreak(player, x, y, z)){
                        clickBlock(world, player, x, y, z, tag.getInteger("side"));
                    }
                }
            }
            return itemStack;
        }
    }
}
