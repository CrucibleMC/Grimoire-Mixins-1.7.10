package io.github.crucible.grimoire.mixins.thaumicenergistics;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumicenergistics.common.container.ContainerPartArcaneCraftingTerminal;

@Mixin(value = ContainerPartArcaneCraftingTerminal.class, remap = false)
public abstract class MixinContainerPartAcraneCraftingTerminal {

    /**
     * @author EverNife
     * @reason There are some dupes caused by the SHIFT-CLICK,
     * better justa disable it...
     *
     *
     * func_82846_b == transferStackInSlot
     */
    @Overwrite
    public ItemStack func_82846_b(EntityPlayer player, int slotNumber) {
        return null;
    }
}