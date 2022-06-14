package io.github.crucible.grimoire.mixins.appliedenergistics;

import appeng.util.Platform;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.security.InvalidParameterException;
import java.util.UUID;
import java.util.WeakHashMap;

@Mixin(value = Platform.class, remap = false)
public abstract class MixinPlatform {

    @Shadow
    @Final
    private static final WeakHashMap<World, EntityPlayer> FAKE_PLAYERS = new WeakHashMap<World, EntityPlayer>();

    /**
     * @author Rehab_CZ
     * @reason Change FakePlayer instead of [Minecraft] to [AppliedEnergistics]
     */
    @Overwrite
    public static EntityPlayer getPlayer(final WorldServer w )
    {
        if( w == null )
        {
            throw new InvalidParameterException( "World is null." );
        }

        final EntityPlayer wrp = FAKE_PLAYERS.get( w );
        if( wrp != null )
        {
            return wrp;
        }

        GameProfile AE2Profile = new GameProfile(UUID.nameUUIDFromBytes("[AppliedEnergistics]".getBytes()), "[AppliedEnergistics]");

        final EntityPlayer p = FakePlayerFactory.get(w,AE2Profile);
        FAKE_PLAYERS.put( w, p );
        return p;
    }


}
