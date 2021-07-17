package io.github.crucible.grimoire.mixins.minefactoryreloaded;

import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import powercrystals.minefactoryreloaded.api.rednet.connectivity.IRedNetNoConnection;
import powercrystals.minefactoryreloaded.block.BlockRubberSapling;
import powercrystals.minefactoryreloaded.world.WorldGenRubberTree;

import java.util.Random;

@Mixin(value = BlockRubberSapling.class, remap = false)
public abstract class MixinBlockRubberSapling extends BlockSapling implements IRedNetNoConnection {

    @Shadow
    private static WorldGenRubberTree treeGen;

    /**
     * @author EverNife
     * @reason Disable the most screwed tree on the entire mod_land, that it's only purpose is to crash servers!
     */
    @Overwrite
    public void func_149878_d(World world, int xCoord, int yCoord, int zCoord, Random rand) {
        //"Function growTree"
        if (!world.isRemote && TerrainGen.saplingGrowTree(world, rand, xCoord, yCoord, zCoord)) {
            world.setBlockToAir(xCoord, yCoord, zCoord);

            //"Function growTree but from the treeGen"
            if (treeGen.growTree(world, rand, xCoord, yCoord, zCoord)) {
                return;
            }
            world.setBlock(xCoord, yCoord, zCoord, this, 0, 4);
        }
    }

}
