package io.github.crucible.grimoire.mixins.avaritia;

import fox.spiteful.avaritia.items.tools.ItemSwordInfinity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemSwordInfinity.class, remap = false)
public abstract class MixinLudicrousSword {

    @Redirect(method = "func_77644_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;func_70645_a(Lnet/minecraft/util/DamageSource;)V"))
    private void victimProxy(EntityLivingBase victim, DamageSource src) {

        if (!victim.isDead && victim.getHealth() > 0)
            victim.onDeath(src); // TODO: 17/10/2019 Nope, isso não funcionou, talvez agora funcione?
    }
}
