package io.github.crucible.grimoire.mixins.avaritia;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "fox/spiteful/avaritia/entity/EntityGapingVoid", remap = false)
public abstract class MixinLudicrousFix extends Entity {
    public MixinLudicrousFix(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    /**
     * @author juanmuscaria
     * @reason Desabilitar o buraco negro do avaritia.
     */
    @Overwrite
    public void func_70071_h_() {
        super.onUpdate();
        this.setDead(); //Nope, quem achou que um buraco negro portátil era uma boa ideia?
    }
}
