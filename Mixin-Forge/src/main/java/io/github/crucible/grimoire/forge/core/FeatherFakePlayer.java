package io.github.crucible.grimoire.forge.core;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class FeatherFakePlayer extends FakePlayer {
    private final UserIdent owner;

    FeatherFakePlayer(WorldServer world, GameProfile name, String owner) {
        super(world, name);
        this.owner = UserIdent.create((UUID) null, owner);
    }

    FeatherFakePlayer(WorldServer world, GameProfile name, UserIdent owner) {
        super(world, name);
        this.owner = owner;
    }

    public UserIdent getOwner() {
        return owner;
    }
}
