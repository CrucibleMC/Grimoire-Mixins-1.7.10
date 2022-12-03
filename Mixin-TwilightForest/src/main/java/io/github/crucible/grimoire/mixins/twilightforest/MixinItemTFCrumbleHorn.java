package io.github.crucible.grimoire.mixins.twilightforest;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.block.TFBlocks;
import twilightforest.item.ItemTF;
import twilightforest.item.ItemTFCrumbleHorn;

@Mixin(value = ItemTFCrumbleHorn.class, remap = false)
public abstract class MixinItemTFCrumbleHorn extends ItemTF {

    @Shadow
    @Final
    private static int CHANCE_HARVEST;

    @Shadow
    @Final
    private static int CHANCE_CRUMBLE;

    @Shadow
    protected abstract int crumbleBlocksInAABB(World world, EntityPlayer player, AxisAlignedBB par1AxisAlignedBB);

    /**
     * @author Rehab_CZ
     * @reason Fire events before crumble
     */
    @Overwrite
    private int doCrumble(World world, EntityPlayer player) {

        if (EventUtils.cantBreak(player, player.posX, player.posY, player.posZ)) {
            return 0; //Stop crumble if can't break at its own location
        }

        double range = 3.0D;
        double radius = 2.0D;
        Vec3 srcVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 lookVec = player.getLookVec();
        Vec3 destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);

        AxisAlignedBB crumbleBox = AxisAlignedBB.getBoundingBox(destVec.xCoord - radius, destVec.yCoord - radius, destVec.zCoord - radius, destVec.xCoord + radius, destVec.yCoord + radius, destVec.zCoord + radius);

        return crumbleBlocksInAABB(world, player, crumbleBox);
    }


    /**
     * @author Rehab_CZ
     * @reason Fire events before crumble
     */
    @Overwrite
    private int crumbleBlock(World world, EntityPlayer player, int dx, int dy, int dz) {
        int cost = 0;

        Block currentID = world.getBlock(dx, dy, dz);

        if (EventUtils.cantBreak(player, dx, dy, dz)) {
            return 0;
        }

        if (currentID != Blocks.air) {
            int currentMeta = world.getBlockMetadata(dx, dy, dz);

            if (currentID == Blocks.stone && world.rand.nextInt(CHANCE_CRUMBLE) == 0) {
                world.setBlock(dx, dy, dz, Blocks.cobblestone, 0, 3);
                world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                cost++;
            }

            if (currentID == Blocks.stonebrick && currentMeta == 0 && world.rand.nextInt(CHANCE_CRUMBLE) == 0) {
                world.setBlock(dx, dy, dz, Blocks.stonebrick, 2, 3);
                world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                cost++;
            }

            if (currentID == TFBlocks.mazestone && currentMeta == 1 && world.rand.nextInt(CHANCE_CRUMBLE) == 0) {
                world.setBlock(dx, dy, dz, TFBlocks.mazestone, 4, 3);
                world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                cost++;
            }

            if (currentID == Blocks.cobblestone && world.rand.nextInt(CHANCE_CRUMBLE) == 0) {
                world.setBlock(dx, dy, dz, Blocks.gravel, 0, 3);
                world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                cost++;
            }

            if (currentID == Blocks.gravel || currentID == Blocks.dirt) {
                if (currentID.canHarvestBlock(player, currentMeta) && world.rand.nextInt(CHANCE_HARVEST) == 0) {
                    world.setBlock(dx, dy, dz, Blocks.air, 0, 3);
                    currentID.harvestBlock(world, player, dx, dy, dz, currentMeta);
                    world.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                    cost++;
                }
            }
        }
        return cost;
    }

}
