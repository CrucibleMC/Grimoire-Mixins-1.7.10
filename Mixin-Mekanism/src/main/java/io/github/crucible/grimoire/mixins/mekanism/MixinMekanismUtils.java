package io.github.crucible.grimoire.mixins.mekanism;

import io.github.crucible.grimoire.data.mekanism.ItemRecipeResult;
import mekanism.common.util.MekanismUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;
import java.util.Iterator;

@Mixin(value = MekanismUtils.class, remap = false)
public abstract class MixinMekanismUtils {

    private static HashMap<String, ItemRecipeResult> cacheOfResults = new HashMap();

    /**
     * @author EverNife
     * @reason Creates a cache for the recipes found to prevent high CPU usage.
     *
     * May cause inconsistency on ULTRA RARE CASES, where you refresh the recipes,
     * for example with CraftTweaker after they had been unsed by this class.
     *
     * Note, this optimization will not work for items with NBT
     *
     */
    @Overwrite
    public static ItemStack findMatchingRecipe(InventoryCrafting inv, World world) {
        ItemStack[] dmgItems = new ItemStack[2];

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            if (inv.getStackInSlot(i) != null) {
                if (dmgItems[0] != null) {
                    dmgItems[1] = inv.getStackInSlot(i);
                    break;
                }

                dmgItems[0] = inv.getStackInSlot(i);
            }
        }

        if (dmgItems[0] != null && dmgItems[0].getItem() != null) {
            if (dmgItems[1] != null && dmgItems[0].getItem() == dmgItems[1].getItem() && dmgItems[0].stackSize == 1 && dmgItems[1].stackSize == 1 && dmgItems[0].getItem().isRepairable()) {
                Item theItem = dmgItems[0].getItem();
                int dmgDiff0 = theItem.getMaxDamage() - dmgItems[0].getItemDamageForDisplay();
                int dmgDiff1 = theItem.getMaxDamage() - dmgItems[1].getItemDamageForDisplay();
                int value = dmgDiff0 + dmgDiff1 + theItem.getMaxDamage() * 5 / 100;
                int solve = Math.max(0, theItem.getMaxDamage() - value);
                return new ItemStack(dmgItems[0].getItem(), 1, solve);
            } else {

                //Grimoire Start
                StringBuilder identifier = new StringBuilder();
                boolean hasNBT = false;
                for (int i = 0; i < inv.getSizeInventory(); ++i){
                    ItemStack itemstack1 = inv.getStackInSlot(i);
                    if (itemstack1 != null){
                        if (itemstack1.hasTagCompound()){
                            hasNBT = true;
                            break;
                        }
                        identifier.append(itemstack1.getItem().getUnlocalizedName() + "---" +  itemstack1.getItemDamage());
                    }
                }

                if (!hasNBT){
                    ItemRecipeResult result = cacheOfResults.get(identifier.toString());
                    if (result != null) {
                        return result.copyItemStack();
                    }
                }

                Iterator var8 = CraftingManager.getInstance().getRecipeList().iterator();
                IRecipe recipe;
                do {
                    if (!var8.hasNext()) {
                        return null;
                    }
                    recipe = (IRecipe)var8.next();
                } while(!recipe.matches(inv, world));

                ItemStack finalResult = recipe.getCraftingResult(inv);

                if (!hasNBT){
                    cacheOfResults.put(identifier.toString(), new ItemRecipeResult(finalResult.copy()));
                }
                return finalResult;
            }
        } else {
            return null;
        }
    }
}