package io.github.crucible.grimoire.mixins.botania;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.entity.EntityThornChakram;

@Mixin(value = EntityThornChakram.class, remap = false)
public abstract class MixinEntityThornChakram extends EntityThrowable {

    public MixinEntityThornChakram(World world) {
        super(world);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (getThrower() instanceof EntityPlayer && pos.entityHit instanceof EntityLivingBase && EventUtils.cantDamage(this.getThrower(), pos.entityHit)) {
            setDead();
            ci.cancel();
        }

    }

}