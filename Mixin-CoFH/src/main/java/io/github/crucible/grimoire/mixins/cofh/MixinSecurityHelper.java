package io.github.crucible.grimoire.mixins.cofh;

import cofh.lib.util.helpers.SecurityHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.UUID;

@Mixin(value = SecurityHelper.class, remap = false)
public class MixinSecurityHelper {

    /**
     * @author juanmuscaria
     * @reason Remove a stacktrace quando não conseguir baixar o profile do jogador.
     */
    @Overwrite
    public static GameProfile getProfile(UUID uuid, String name) {
        try {
            GameProfile var2 = MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
            if (var2 == null) {
                GameProfile var3 = new GameProfile(uuid, name);
                var2 = MinecraftServer.getServer().func_147130_as().fillProfileProperties(var3, true);
                if (var2 != var3) {
                    MinecraftServer.getServer().func_152358_ax().func_152649_a(var2);
                }
            }
            return var2;
        } catch (Exception ignore) {
            return null;
        }
    }
}
