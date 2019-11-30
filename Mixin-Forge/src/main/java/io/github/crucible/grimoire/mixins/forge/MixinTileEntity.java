package io.github.crucible.grimoire.mixins.forge;

import io.github.crucible.grimoire.forge.core.FakePlayerManager;
import io.github.crucible.grimoire.forge.core.ITileEntity;
import io.github.crucible.grimoire.forge.core.UserIdent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity implements ITileEntity {

    private UserIdent tileOwner = UserIdent.nobody;

    @Shadow
    public abstract World getWorldObj();


    @Override
    public UserIdent getUserIdent() {
        return tileOwner;
    }


    public void setUserIdent(UserIdent ident) {
        tileOwner = ident;
    }

    @Override
    public EntityPlayer getFakePlayer() {
        if (tileOwner.equals(UserIdent.nobody)) {
            return FakePlayerManager.get((WorldServer) getWorldObj());
        } else {
            return FakePlayerManager.get((WorldServer) getWorldObj(), tileOwner);
        }
    }

    @Inject(method = "readFromNBT", at = @At("HEAD"))
    private void readInject(NBTTagCompound nbtTagCompound, CallbackInfo callback) {
        if (UserIdent.existsInNbt(nbtTagCompound, "crucibledata.owner"))
            tileOwner = UserIdent.readfromNbt(nbtTagCompound, "crucibledata.owner");
        else tileOwner = UserIdent.nobody;

    }

    @Inject(method = "writeToNBT", at = @At("HEAD"))
    private void writeInject(NBTTagCompound nbtTagCompound, CallbackInfo callback) {
        tileOwner.saveToNbt(nbtTagCompound, "crucibledata.owner");
    }
}
