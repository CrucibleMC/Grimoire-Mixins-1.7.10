package io.github.crucible.grimoire.mixins.enderio;

import crazypants.enderio.api.teleport.TravelSource;
import crazypants.enderio.teleport.packet.PacketTravelEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PacketTravelEvent.class, remap = false)
public abstract class MixinPacketTravelEvent {

    /**
     * @author evernife
     * @reason Disable teleport if entity is more than 250 blocks away from the target
     */
    @Inject(method = "doServerTeleport", at = @At("HEAD"), cancellable = true)
    private static void doServerTeleport(Entity toTp, int x, int y, int z, int powerUse, boolean conserveMotion, TravelSource source, CallbackInfoReturnable<Boolean> cir) {
        int xOrigin = MathHelper.floor_double(toTp.posX);
        int zOrigin = MathHelper.floor_double(toTp.posZ);

        int squareDistance = (z - zOrigin) * (z - zOrigin) + (x - xOrigin) * (x - xOrigin);

        if (squareDistance >= 122500){ //sqrt(122500) == 350
            cir.setReturnValue(true);
            return;
        }
    }

}
