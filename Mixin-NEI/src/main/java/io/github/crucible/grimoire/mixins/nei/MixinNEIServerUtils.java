package io.github.crucible.grimoire.mixins.nei;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.*;
import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

/**
 * Created by EverNife on 22/08/2019.
 */

@Mixin(value = NEIServerUtils.class, remap = false)
public abstract class MixinNEIServerUtils {

    @Shadow
    public static WorldSettings.GameType getGameType(int mode) {
        return null;
    }

    /**
     * @author EverNife
     * @reason Previnir hackers que acabam burlando o sistema de packets!
     *
     * Adicionado a permissão "nei.gamemode" para validar o processo!
     */
    @Overwrite
    public static void setGamemode(EntityPlayerMP player, int mode) {
        if (mode < 0 || mode >= NEIActions.gameModes.length ||
                NEIActions.nameActionMap.containsKey(NEIActions.gameModes[mode]) &&
                        !NEIServerConfig.canPlayerPerformAction(player.getCommandSenderName(), NEIActions.gameModes[mode]))
            return;

        //creative+
        NEIServerConfig.forPlayer(player.getCommandSenderName()).enableAction("creative+", mode == 2);
        if(mode == 2 && !(player.openContainer instanceof ContainerCreativeInv))//open the container immediately for the client
            NEISPH.processCreativeInv(player, true);

        //change it on the server
        WorldSettings.GameType newGamemode = getGameType(mode);
        NEIServerConfig.logger.info("[NEI] Changing player " + player.getCommandSenderName() + " gamemode to [" + newGamemode + "]");
        MinecraftServer.getServer().getCommandManager().executeCommand(player, "gamemode " + newGamemode);

        if (EventUtils.hasPermission(player,"nei.gamemode")){
            player.theItemInWorldManager.setGameType(newGamemode);

            //tell the client to change it
            (new PacketCustom("NEI", 14)).writeByte(mode).sendToPlayer(player);
            player.addChatMessage(new ChatComponentText("§aGamemode alterado para [" + newGamemode.getName() + "]"));
        }
    }

}