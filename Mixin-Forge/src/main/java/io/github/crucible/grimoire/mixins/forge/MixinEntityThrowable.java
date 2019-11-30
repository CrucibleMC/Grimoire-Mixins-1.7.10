package io.github.crucible.grimoire.mixins.forge;


import io.github.crucible.grimoire.forge.core.IThrowableHook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityThrowable.class)
public abstract class MixinEntityThrowable implements IThrowableHook {
    @Shadow
    private EntityLivingBase thrower;

    @Override
    public void setThrower(EntityLivingBase player) {
        thrower = player;
    }
}
