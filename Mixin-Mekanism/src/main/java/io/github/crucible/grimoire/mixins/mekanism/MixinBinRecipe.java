package io.github.crucible.grimoire.mixins.mekanism;

import mekanism.common.recipe.BinRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// TODO: 17/10/2019 Corrigir o recipe ao invés de desativar.
@Mixin(value = BinRecipe.class, remap = false)
public abstract class MixinBinRecipe {

    /**
     * @author juanmuscaria
     * @reason Desativa os recipes do silo.
     */
    @Overwrite
    public ItemStack getResult(IInventory inv) {
        return null;
    }

}