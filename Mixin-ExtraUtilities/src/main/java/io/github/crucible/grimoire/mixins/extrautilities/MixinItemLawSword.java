package io.github.crucible.grimoire.mixins.extrautilities;

import com.rwtema.extrautils.item.ItemLawSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemLawSword.class, remap = true)
public abstract class MixinItemLawSword extends ItemSword {

    public MixinItemLawSword(ToolMaterial p_i45356_1_) {
        super(p_i45356_1_);
    }

    /**
     * @author EverNife
     * @reason Disable Kikoku killing on click
     */
    @Overwrite(remap = true)
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return true; //Disable this
    }

}
