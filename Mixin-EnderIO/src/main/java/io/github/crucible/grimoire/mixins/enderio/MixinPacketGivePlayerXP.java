package io.github.crucible.grimoire.mixins.enderio;

import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import crazypants.enderio.xp.IHaveExperience;
import crazypants.enderio.xp.PacketGivePlayerXP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = PacketGivePlayerXP.class, remap = false)
public abstract class MixinPacketGivePlayerXP {

    @Inject(method = "onMessage", at = @At(target = "Lcrazypants/enderio/xp/IHaveExperience;getContainer()Lcrazypants/enderio/xp/ExperienceContainer;", value = "INVOKE"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void beforeGiveXp(PacketGivePlayerXP message, MessageContext ctx, CallbackInfoReturnable<IMessage> ci, EntityPlayer player, TileEntity tile, IHaveExperience xpTile) {
        if (EventUtils.cantBreak(player, tile.xCoord, tile.yCoord, tile.zCoord)) ci.setReturnValue(null);
    }
}
