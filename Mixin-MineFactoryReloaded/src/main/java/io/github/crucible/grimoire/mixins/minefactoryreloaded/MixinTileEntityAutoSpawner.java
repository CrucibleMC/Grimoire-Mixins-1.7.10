package io.github.crucible.grimoire.mixins.minefactoryreloaded;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityAutoSpawner;

@Mixin(value = TileEntityAutoSpawner.class, remap = false)
public abstract class MixinTileEntityAutoSpawner  {

    @Shadow
    protected boolean _spawnExact = false;

    @Inject(method = "activateMachine", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfoReturnable<Boolean> cir) {
        if (_spawnExact) _spawnExact = false;
    }

}
