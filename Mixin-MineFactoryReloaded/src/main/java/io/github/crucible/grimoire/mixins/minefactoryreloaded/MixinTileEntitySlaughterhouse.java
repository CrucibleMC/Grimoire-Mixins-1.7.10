package io.github.crucible.grimoire.mixins.minefactoryreloaded;

import io.github.crucible.grimoire.data.minefactoryreloaded.IHasGrindingWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySlaughterhouse;

@Mixin(value = TileEntitySlaughterhouse.class, remap = false)
public abstract class MixinTileEntitySlaughterhouse {

    /**
     * @author EverNife
     * @reason Fix NPE on activateMachine in some rare cases
     *
     */
    @Inject(method = "activateMachine", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfoReturnable<Boolean> cir) {
        IHasGrindingWorld this_tile = (IHasGrindingWorld) this;
        if (this_tile.getGrindingWorld() == null) cir.setReturnValue(false);
    }

}
