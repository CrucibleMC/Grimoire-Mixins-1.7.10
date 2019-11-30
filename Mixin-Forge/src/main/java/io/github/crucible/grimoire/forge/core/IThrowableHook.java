package io.github.crucible.grimoire.forge.core;

import net.minecraft.entity.EntityLivingBase;

public interface IThrowableHook {
    void setThrower(EntityLivingBase player);
}
