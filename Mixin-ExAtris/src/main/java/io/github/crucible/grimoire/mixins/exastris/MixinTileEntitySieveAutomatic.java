package io.github.crucible.grimoire.mixins.exastris;

import ExAstris.Block.TileEntity.TileEntitySieveAutomatic;
import ExAstris.Data.ModData;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.lib.util.helpers.ItemHelper;
import exnihilo.registries.SieveRegistry;
import exnihilo.registries.helpers.SiftingResult;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(value = TileEntitySieveAutomatic.class)
public abstract class MixinTileEntitySieveAutomatic extends TileEntity implements IEnergyHandler, ISidedInventory {

    @Shadow private float volume;

    @Shadow public abstract float getEffectiveSpeed();

    @Shadow public EnergyStorage storage;

    @Shadow public abstract int getEffectiveEnergy();

    @Shadow public TileEntitySieveAutomatic.SieveMode mode;

    @Shadow public Block content;

    @Shadow public int contentMeta;

    @Shadow public abstract int getFortuneModifier();

    @Shadow protected ItemStack[] inventory;

    @Shadow private boolean particleMode;

    @Shadow private boolean update;

    /**
     * @author EverNife
     * @reason Disable the drop of items on the groud
     * to prevent situations like this: https://cdn.discordapp.com/attachments/573598615939973128/830637998152351744/unknown.png
     */
    @Overwrite(remap = false)
    public void ProcessContents()
    {

        volume -= getEffectiveSpeed();
        storage.extractEnergy(getEffectiveEnergy(), false);

        if (volume <= 0)
        {
            mode = TileEntitySieveAutomatic.SieveMode.EMPTY;
            //give rewards!
            if (!worldObj.isRemote)
            {
                ArrayList<SiftingResult> rewards = SieveRegistry.getSiftingOutput(content, contentMeta);
                if (rewards != null && rewards.size() > 0)
                {
                    Iterator<SiftingResult> it = rewards.iterator();
                    while(it.hasNext())
                    {
                        SiftingResult reward = it.next();
                        int fortuneAmount;
                        if (ModData.sieveFortuneExtraRolls)
                            fortuneAmount = getFortuneModifier();
                        else
                            fortuneAmount = 1;

                        for (int fortuneCounter = 0; fortuneCounter < fortuneAmount; fortuneCounter++)
                        {
                            int size = getSizeInventory()-2;
                            int inventoryIndex = 0;
                            if (worldObj.rand.nextInt(reward.rarity) == 0)
                            {
                                int fortuneAmount2;
                                if (ModData.sieveFortuneExtraDrops)
                                    fortuneAmount2=getFortuneModifier();
                                else
                                    fortuneAmount2=1;

                                for (int fortuneCounter2 = 0; fortuneCounter2 < fortuneAmount2; fortuneCounter2++)
                                {
                                    for(int i = 1; i < size; i++)
                                    {
                                        if(inventory[i] == null)
                                        {
                                            inventoryIndex=i;
                                            break;
                                        }
                                        else
                                        {
                                            if (ItemHelper.itemsEqualWithMetadata(inventory[i],new ItemStack(reward.item, 1, reward.meta)) && inventory[i].stackSize < inventory[i].getMaxStackSize())
                                            {
                                                inventoryIndex=i;
                                                break;
                                            }
                                        }
                                    }


                                    if(inventoryIndex != 0)
                                    {
                                        if (inventory[inventoryIndex] != null)
                                            inventory[inventoryIndex] = new ItemStack(reward.item, (inventory[inventoryIndex].stackSize + 1), reward.meta);
                                        else
                                            inventory[inventoryIndex] = new ItemStack(reward.item, 1, reward.meta);
                                    }
                                    /* GRIMOIRE START
                                    else
                                    {
                                        EntityItem entityitem = new EntityItem(worldObj, (double)xCoord + 0.5D, (double)yCoord + 1.5D, (double)zCoord + 0.5D, new ItemStack(reward.item, 1, reward.meta));

                                        double f3 = 0.05F;
                                        entityitem.motionX = worldObj.rand.nextGaussian() * f3;
                                        entityitem.motionY = (0.2d);
                                        entityitem.motionZ = worldObj.rand.nextGaussian() * f3;

                                        worldObj.spawnEntityInWorld(entityitem);

                                    }
                                    GRIMOIRE END
                                    */
                                }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            particleMode = true;
        }

        update = true;
    }
}
