package io.github.crucible.grimoire.mixins.thaumcraft;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;

@Mixin(value = ItemFocusPortableHole.class, remap = false)
public abstract class MixinItemFocusPortableHole {

    /**
     * @author juanmuscaria
     */
    // TODO: 15/10/2019 Refazer toda a lógica do blackhole focus.
    @Inject(method = "onFocusRightClick", at = @At("HEAD"), cancellable = true)
    private void onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop, CallbackInfoReturnable<ItemStack> ci) {
        if (!EventUtils.hasPermission(player, "feather.thaumcraft.focus.blackhole")) {
            player.addChatMessage(new ChatComponentTranslation("servertext.focus.permission"));
            ci.setReturnValue(itemstack);
            ci.cancel();
        }
    }
}
