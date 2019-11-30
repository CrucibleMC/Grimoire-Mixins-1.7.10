package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import io.github.crucible.grimoire.forge.core.IThrowableHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.entity.EntityManaBurst;


@Mixin(value = EntityManaBurst.class, remap = false)
public abstract class MixinEntityManaBurst extends EntityThrowable implements IThrowableHook {

    public MixinEntityManaBurst(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/EntityPlayer;)V", at = @At("RETURN"))
    private void onConstruct(EntityPlayer player, CallbackInfo ci) {
        setThrower(player);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() instanceof EntityPlayer) {
            if (EventUtils.cantBreak((EntityPlayer) this.getThrower(), pos.blockX, pos.blockY, pos.blockZ)) {
                setDead();
                ci.cancel();
            }
        } else {
            setDead();
            ci.cancel();
        }
    }

}
