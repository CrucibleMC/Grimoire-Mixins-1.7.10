package io.github.crucible.grimoire.mixins.industrialcraft;

import ic2.core.block.EntityIC2Explosive;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin (value = EntityIC2Explosive.class, remap = false)
public abstract class MixinEntityIC2Explosive extends Entity {

    @Shadow public float explosivePower;

    public MixinEntityIC2Explosive(String string) {
        super(null);
    }

    /**
     * @author Rehab_CZ
     * @reason USe default explosions
     */
    @Overwrite
    private void explode() {
        this.worldObj.createExplosion(this,posX, posY, posZ, this.explosivePower, true).exploder = this;
    }
}
