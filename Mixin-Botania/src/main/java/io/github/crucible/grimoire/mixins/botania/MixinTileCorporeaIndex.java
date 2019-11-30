package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraftforge.event.ServerChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "vazkii/botania/common/block/tile/corporea/TileCorporeaIndex$InputHandler", remap = false)
public abstract class MixinTileCorporeaIndex {

    @Inject(method = "onChatMessage", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/block/tile/corporea/TileCorporeaIndex;doCorporeaRequest(Ljava/lang/Object;ILvazkii/botania/api/corporea/ICorporeaSpark;)V"), cancellable = true)
    private void doCorporeaRequestProxy(ServerChatEvent event, CallbackInfo ci) {
        if (EventUtils.cantBreak(event.player, event.player.posX, event.player.posY, event.player.posZ)) ci.cancel();
    }

}
