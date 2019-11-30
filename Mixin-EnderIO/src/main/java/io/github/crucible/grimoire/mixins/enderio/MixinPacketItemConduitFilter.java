package io.github.crucible.grimoire.mixins.enderio;

import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import crazypants.enderio.conduit.packet.PacketItemConduitFilter;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(value = PacketItemConduitFilter.class, remap = false)
public abstract class MixinPacketItemConduitFilter {

    @Inject(method = "onMessage", at = @At("HEAD"), cancellable = true)
    private void onMessage(PacketItemConduitFilter message, MessageContext ctx, CallbackInfoReturnable<IMessage> ci) {
        try {
            EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
            int x = getValue(message, "x");
            int y = getValue(message, "y");
            int z = getValue(message, "z");
            if (EventUtils.cantBreak(entityPlayer, x, y, z)) {
                ci.setReturnValue(null);
                ci.cancel();
            }
        } catch (Exception e) {
            ci.setReturnValue(null);
            ci.cancel();
        }
    }

    private int getValue(Object target, String targetField) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(targetField);
        field.setAccessible(true);
        return (int) field.get(target);
    }
}
