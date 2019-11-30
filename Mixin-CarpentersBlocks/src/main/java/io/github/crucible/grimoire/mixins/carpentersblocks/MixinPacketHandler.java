package io.github.crucible.grimoire.mixins.carpentersblocks;

import com.carpentersblocks.network.ICarpentersPacket;
import com.carpentersblocks.util.handler.PacketHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;
import java.util.List;

@Mixin(value = PacketHandler.class, remap = false)
public class MixinPacketHandler {
    @Shadow
    @Final
    private static List<Class> packetCarrier;

    /**
     * @author juanmuscaria
     * @reason Fix para o console spam hack.
     */
    @SubscribeEvent
    @Overwrite
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) throws IOException {

        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
        EntityPlayer entityPlayer = ((NetHandlerPlayServer) event.handler).playerEntity;
        int packetId = bbis.readInt();
        if (packetId < 0 || packetId > packetCarrier.size()) {
            ((NetHandlerPlayServer) event.handler).kickPlayerFromServer("§4Auto-Kicked by System! [0x03]");
            System.out.println("O jogador " + entityPlayer.getGameProfile() + " está possivelmente com hack!");
        }
        try {
            ICarpentersPacket packetClass = (ICarpentersPacket) packetCarrier.get(packetId).newInstance();
            packetClass.processData(entityPlayer, bbis);
        } catch (Exception e) {
            e.printStackTrace();
            ((NetHandlerPlayServer) event.handler).kickPlayerFromServer("§4Auto-Kicked by System! [0x03]");
            System.out.println("O jogador " + entityPlayer.getGameProfile() + " está possivelmente com hack!");
        }

        bbis.close();
    }
}
