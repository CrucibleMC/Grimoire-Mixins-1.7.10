package io.github.crucible.grimoire.mixins.mekanism.containers;

import mekanism.common.inventory.container.ContainerQuantumEntangloporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ContainerQuantumEntangloporter.class, remap = false)
public abstract class MixinContainerQuantumEntangloporter extends Container {

    /**
     * @author EverNife
     * @reason Desativar Shift-Click para prevenir dupes
     */
    @Overwrite
    public ItemStack func_82846_b(EntityPlayer player, int slotID) {
        return null;
    }

}
