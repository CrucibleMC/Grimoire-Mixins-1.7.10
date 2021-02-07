package io.github.crucible.grimoire.data.mekanism;

import net.minecraft.item.ItemStack;

public class ItemRecipeResult {

    ItemStack itemStack;

    public ItemRecipeResult(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack copyItemStack() {
        return itemStack == null ? null : itemStack.copy();
    }
}
