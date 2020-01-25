package io.github.crucible.grimoire.mixins.extrautilities;

import com.gamerforea.eventhelper.util.EventUtils;
import com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderQuarry;
import io.github.crucible.grimoire.forge.core.ITileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created by Frani on 12/08/2017.
 */

@Pseudo
@Mixin(value = TileEntityEnderQuarry.class, remap = false)
public abstract class MixinTileEntityEnderQuarry extends TileEntity implements ITileEntity {

    @Inject(method = "mineBlock", at = @At("HEAD"), cancellable = true)
    public void fireBreak(final int x, final int y, final int z, final boolean replaceWithDirt, CallbackInfoReturnable<Boolean> cir) {
        if (EventUtils.cantBreak(this.getFakePlayer(), x, y, z)) {
            cir.setReturnValue(false);
        }
    }

}
