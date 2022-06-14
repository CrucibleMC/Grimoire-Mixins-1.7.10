package io.github.crucible.grimoire.mixins.appliedenergistics;

import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.entity.EntityTinyTNTPrimed;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = EntityTinyTNTPrimed.class, remap = false)
public abstract class MixinEntityTinyTNTPrimed extends EntityTNTPrimed {

    public MixinEntityTinyTNTPrimed(String string) {
        super(null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author Rehab_CZ
     * @reason Replaced with default explosions (GPP can catch this)
     */
    @Overwrite
    void explode() {
        // Still respect configs tho
        if (AEConfig.instance.isFeatureEnabled(AEFeature.TinyTNTBlockDamage)) {
            this.posY -= 0.25;
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.3f, true).exploder = this;
        }
    }

}
