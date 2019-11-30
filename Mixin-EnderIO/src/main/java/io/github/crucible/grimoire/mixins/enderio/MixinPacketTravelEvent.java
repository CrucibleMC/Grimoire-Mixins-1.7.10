package io.github.crucible.grimoire.mixins.enderio;

import crazypants.enderio.api.teleport.TravelSource;
import crazypants.enderio.teleport.packet.PacketTravelEvent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PacketTravelEvent.class, remap = false)
public abstract class MixinPacketTravelEvent {

    /**
     * @author juanmuscaria
     * @reason Desativar o pacote temporariamente em quanto não tem uma correção definitiva.
     */
    @Overwrite
    public static boolean doServerTeleport(Entity toTp, int x, int y, int z, int powerUse, boolean conserveMotion, TravelSource source) {
        return false;
        //Vai ficar desativado até botar tudo no server side, caso não tiver queda de desempenho será reativado.
    }
}
