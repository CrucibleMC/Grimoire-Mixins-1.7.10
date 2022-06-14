package io.github.crucible.grimoire.mixins.tinkersconstruct;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tconstruct.mechworks.entity.item.ExplosivePrimed;

@Mixin (value = ExplosivePrimed.class, remap = false)
public abstract class MixinExplosivePrimed extends Entity {

    public MixinExplosivePrimed(String string) {
        super(null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author Rehab_CZ
     * @reason Replaced with default explosions (GPP can catch this)
     */
    @Overwrite
    public void createExplosion (World world, Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        world.createExplosion(par1Entity, par2, par4, par6, par8, true).exploder = par1Entity;
    }
}
