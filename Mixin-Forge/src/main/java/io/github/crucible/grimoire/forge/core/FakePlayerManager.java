package io.github.crucible.grimoire.forge.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;

import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FakePlayerManager {
    private static final GameProfile featherModProfile = new GameProfile(offlineUuidFromName("[FeatherMod]"), "[FeatherMod]");
    private static SoftReference<FeatherFakePlayer> featherModFakePlayer = new SoftReference<>(null);
    private static final Cache<String, SoftReference<FeatherFakePlayer>> fakePlayerCache = new Cache<>(6000L, new Function<SoftReference<FeatherFakePlayer>>() {
        @Override
        public boolean run(SoftReference<FeatherFakePlayer> value) {
            return value.get() != null;
        }
    }, new EmptyFunction<>());

    public static FeatherFakePlayer get(WorldServer world, String name) {
        SoftReference<FeatherFakePlayer> reference = fakePlayerCache.get(name);
        if (reference == null || reference.get() == null) {
            String fName = "[" + name + "]";
            GameProfile profile = new GameProfile(offlineUuidFromName(fName), fName);
            FeatherFakePlayer player = new FeatherFakePlayer(world, profile, name);
            reference = new SoftReference<FeatherFakePlayer>(player);
        }
        FeatherFakePlayer fakePlayer = reference.get();
        assert fakePlayer != null;
        fakePlayer.worldObj = world;
        return fakePlayer;
    }

    public static FeatherFakePlayer get(WorldServer world, UserIdent name) {
        SoftReference<FeatherFakePlayer> reference = fakePlayerCache.get(name.getPlayerName());
        if (reference == null || reference.get() == null) {
            String fName = "[" + name.getPlayerName() + "]";
            GameProfile profile = new GameProfile(offlineUuidFromName(fName), fName);
            FeatherFakePlayer player = new FeatherFakePlayer(world, profile, name);
            reference = new SoftReference<FeatherFakePlayer>(player);
        }
        FeatherFakePlayer fakePlayer = reference.get();
        assert fakePlayer != null;
        fakePlayer.worldObj = world;
        return fakePlayer;
    }

    public static FeatherFakePlayer get(WorldServer world) {
        FeatherFakePlayer player = featherModFakePlayer.get();
        if (player == null) {
            player = new FeatherFakePlayer(world, featherModProfile, "");
            featherModFakePlayer = new SoftReference<>(player);
        }
        player.worldObj = world;
        return player;
    }

    private static UUID offlineUuidFromName(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
    }
}
