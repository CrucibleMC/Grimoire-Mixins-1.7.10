package io.github.crucible.grimoire.mixins.extrautilities;

import com.rwtema.extrautils.item.ItemLawSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemLawSword.class)
public abstract class MixinItemLawSword {

    /**
     * @author EverNife
     * @reason Disable Kikoku killing on click
     */
    @Overwrite(remap = false)
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return true; //Disable this
    }

}
