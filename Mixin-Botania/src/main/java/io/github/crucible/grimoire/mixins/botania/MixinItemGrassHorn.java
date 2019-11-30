package io.github.crucible.grimoire.mixins.botania;


import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.botania.api.item.IGrassHornExcempt;
import vazkii.botania.api.item.IHornHarvestable;
import vazkii.botania.api.subtile.ISpecialFlower;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.ItemGrassHorn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(value = ItemGrassHorn.class, remap = false)
public abstract class MixinItemGrassHorn {

    @Redirect(method = "onUsingTick", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/item/ItemGrassHorn;breakGrass(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V"))
    private void breakGrassProxy(World world, ItemStack stack, int stackDmg, int srcx, int srcy, int srcz, ItemStack stack2, EntityPlayer player, int time) {
        breakGrassFix(world, stack, stackDmg, srcx, srcy, srcz, player);
    }

    @SuppressWarnings({"CollectionAddAllCanBeReplacedWithConstructor", "unchecked"})
    private void breakGrassFix(World world, ItemStack stack, int stackDmg, int srcx, int srcy, int srcz, EntityPlayer player) {
        IHornHarvestable.EnumHornType type = IHornHarvestable.EnumHornType.getTypeForMeta(stackDmg);
        Random rand = new Random(srcx ^ srcy ^ srcz);
        int range = 12 - stackDmg * 3;
        int rangeY = 3 + stackDmg * 4;
        List<ChunkCoordinates> coords = new ArrayList();

        for (int i = -range; i < range + 1; i++)
            for (int j = -range; j < range + 1; j++)
                for (int k = -rangeY; k < rangeY + 1; k++) {
                    int x = srcx + i;
                    int y = srcy + k;
                    int z = srcz + j;
                    if (EventUtils.cantBreak(player, x, y, z)) continue;
                    Block block = world.getBlock(x, y, z);
                    if (block instanceof IHornHarvestable ? ((IHornHarvestable) block).canHornHarvest(world, x, y, z, stack, type) : stackDmg == 0 && block instanceof BlockBush && !(block instanceof ISpecialFlower) && (!(block instanceof IGrassHornExcempt) || ((IGrassHornExcempt) block).canUproot(world, x, y, z)) || stackDmg == 1 && block.isLeaves(world, x, y, z) || stackDmg == 2 && block == Blocks.snow_layer)
                        coords.add(new ChunkCoordinates(x, y, z));
                }

        Collections.shuffle(coords, rand);

        int count = Math.min(coords.size(), 32 + stackDmg * 16);
        for (int i = 0; i < count; i++) {
            ChunkCoordinates currCoords = coords.get(i);
            List<ItemStack> items = new ArrayList();
            Block block = world.getBlock(currCoords.posX, currCoords.posY, currCoords.posZ);
            int meta = world.getBlockMetadata(currCoords.posX, currCoords.posY, currCoords.posZ);
            items.addAll(block.getDrops(world, currCoords.posX, currCoords.posY, currCoords.posZ, meta, 0));

            if (block instanceof IHornHarvestable && ((IHornHarvestable) block).hasSpecialHornHarvest(world, currCoords.posX, currCoords.posY, currCoords.posZ, stack, type))
                ((IHornHarvestable) block).harvestByHorn(world, currCoords.posX, currCoords.posY, currCoords.posZ, stack, type);
            else if (!world.isRemote) {
                world.setBlockToAir(currCoords.posX, currCoords.posY, currCoords.posZ);
                if (ConfigHandler.blockBreakParticles)
                    world.playAuxSFX(2001, currCoords.posX, currCoords.posY, currCoords.posZ, Block.getIdFromBlock(block) + (meta << 12));

                for (ItemStack stack_ : items)
                    world.spawnEntityInWorld(new EntityItem(world, currCoords.posX + 0.5, currCoords.posY + 0.5, currCoords.posZ + 0.5, stack_));
            }
        }
    }

}