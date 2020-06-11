package io.github.crucible.grimoire.mixins.magicalcrops;

import com.mark719.magicalcrops.seedbags.SeedContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = SeedContainer.class, remap = false)
public abstract class MixinSeedContainer extends Container {

    /**
     * @author EverNife
     * @reason Disable the Keyboard logic on the server-side to prevent 'java.lang.ClassNotFoundException: org.lwjgl.input.Keyboard'
     *
     *   func_75144_a = slotClick
     */
    @Overwrite
    public ItemStack func_75144_a(int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
        /*
        if (paramInt3 == 4 && paramEntityPlayer.worldObj.isRemote) {
            return !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54) ? super.slotClick(paramInt1, paramInt2, 0, paramEntityPlayer) : this.transferStackInSlot(paramEntityPlayer, paramInt1);
        } else {
            return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
        }
        */
        return super.slotClick(paramInt1, paramInt2, paramInt3, paramEntityPlayer);
    }
}
