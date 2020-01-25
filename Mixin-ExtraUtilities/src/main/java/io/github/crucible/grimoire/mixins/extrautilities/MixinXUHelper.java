package io.github.crucible.grimoire.mixins.extrautilities;

import com.rwtema.extrautils.helper.XUHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by Frani on 13/06/2017.
 */

@Pseudo
@Mixin(value = XUHelper.class, remap = false)
public abstract class MixinXUHelper {

    @Inject(method = "drainBlock", at = @At("HEAD"), cancellable = true)
    private static void injectDrainBlock(final World world, final int x, final int y, final int z, final boolean doDrain, CallbackInfoReturnable cir) {
        if(!world.blockExists(x, y, z)) {
            cir.cancel();
        }
    }

}
