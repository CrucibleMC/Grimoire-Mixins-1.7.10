package io.github.crucible.grimoire.mixins.minefactoryreloaded;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityGrinder;
import powercrystals.minefactoryreloaded.tile.machine.TileEntitySlaughterhouse;
import powercrystals.minefactoryreloaded.world.GrindingWorldServer;

@Mixin(value = TileEntitySlaughterhouse.class, remap = false)
public abstract class MixinTileEntitySlaughterhouse  extends TileEntityGrinder {

    /**
     * @author EverNife
     * @reason Fix NPE on activateMachine in some rare cases
     *
     */
    @Inject(method = "activateMachine", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfoReturnable<Boolean> cir) {
        if (super._grindingWorld == null) cir.setReturnValue(false);
    }

}
