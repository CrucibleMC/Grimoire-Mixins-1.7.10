package io.github.crucible.grimoire.mixins.minefactoryreloaded;

import io.github.crucible.grimoire.data.minefactoryreloaded.IHasGrindingWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityGrinder;
import powercrystals.minefactoryreloaded.world.GrindingWorldServer;

@Mixin(value = TileEntityGrinder.class, remap = false)
public abstract class MixinTileEntityGrinder implements IHasGrindingWorld {

    @Shadow protected GrindingWorldServer _grindingWorld;

    /**
     * @author EverNife
     * @reason Fix NPE on activateMachine in some rare cases
     *
     */
    @Inject(method = "activateMachine", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfoReturnable<Boolean> cir) {
        if (_grindingWorld == null) cir.setReturnValue(false);
    }

    @Override
    public GrindingWorldServer getGrindingWorld() {
        return this._grindingWorld;
    }
}
