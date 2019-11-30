package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.entity.EntityVineBall;

@Mixin(value = EntityVineBall.class, remap = false)
public abstract class MixinEntityVineBall extends EntityThrowable {
    public MixinEntityVineBall(World world) {
        super(world);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() instanceof EntityPlayer) {
            if (EventUtils.cantBreak((EntityPlayer) this.getThrower(), pos.blockX, pos.blockY, pos.blockZ)) {
                setDead();
                ci.cancel();
            }
        }
    }

}
