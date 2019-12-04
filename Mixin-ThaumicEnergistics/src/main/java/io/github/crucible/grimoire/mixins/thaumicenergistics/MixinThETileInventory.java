package io.github.crucible.grimoire.mixins.thaumicenergistics;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumicenergistics.common.tiles.abstraction.ThETileInventory;

@Mixin(value = ThETileInventory.class, remap = false)
public abstract class MixinThETileInventory {

    /**
     * @author EverNife
     * @reason Tentativa resolve o dupe;
     *
     * Testes em campo foram inconclusivos quantos a eficácia dessa mudança!
     *
     *
     * func_70298_a == decrStackSize
     * \--> Removes from an inventory slot (first arg) up to a
     * specified number (second arg) of items and returns them
     * in a new stack.
     */
    @Overwrite
    public ItemStack func_70298_a(int slotIndex, int amount) {
        return null;
    }

}