package io.github.crucible.grimoire.forge.core;

import net.minecraft.entity.player.EntityPlayer;

public interface ITileEntity {
    UserIdent getUserIdent();

    void setUserIdent(UserIdent ident);

    EntityPlayer getFakePlayer();
}
