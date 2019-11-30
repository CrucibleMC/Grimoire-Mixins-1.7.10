package io.github.crucible.grimoire.mixins.botania;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import vazkii.botania.common.entity.EntityEnderAirBottle;

@Mixin(value = EntityEnderAirBottle.class, remap = false)
public abstract class MixinEntityEnderAirBottle extends EntityThrowable {
    public MixinEntityEnderAirBottle(World world) {
        super(world);
    }

    /**
     * @author juanmuscaria
     * @reason Desativa o impacto da garrafa.
     */
    @Overwrite
    protected void func_70184_a(MovingObjectPosition pos) {
        setDead();
    }

}