package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.rod.ItemGravityRod;

@Mixin(value = ItemGravityRod.class, remap = false)
public abstract class MixinItemGravityRod {

    @Redirect(method = "func_77659_a", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/item/rod/ItemGravityRod;setEntityMotionFromVector(Lnet/minecraft/entity/Entity;Lvazkii/botania/common/core/helper/Vector3;F)V"))
    private void setEntityMotionFromVectorProxy(Entity entity, Vector3 originalPosVector, float modifier, ItemStack stack, World world, EntityPlayer player) {
        if (EventUtils.cantDamage(player, entity)) return;
        ItemGravityRod.setEntityMotionFromVector(entity, originalPosVector, modifier);
    }

}
