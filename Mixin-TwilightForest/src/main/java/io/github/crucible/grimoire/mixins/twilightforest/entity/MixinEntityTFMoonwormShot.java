package io.github.crucible.grimoire.mixins.twilightforest.entity;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMoonwormShot;
import twilightforest.item.TFItems;

@Mixin(value = EntityTFMoonwormShot.class, remap = false)
public abstract class MixinEntityTFMoonwormShot extends EntityThrowable {

    public MixinEntityTFMoonwormShot(String someRandom) {
        super(null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author Rehab_CZ
     * @reason Check player instance and fire events
     */
    @Overwrite
    protected void onImpact(MovingObjectPosition mop) {

        EntityLivingBase thrower = this.getThrower();

        if (thrower == null || !(thrower instanceof EntityPlayerMP) || thrower instanceof FakePlayer) {
            return;
        }

        if (EventUtils.cantBreak((EntityPlayer) thrower, mop.blockX, mop.blockY, mop.blockZ)) {
            return;
        }

        if (mop.typeOfHit == MovingObjectType.BLOCK) {
            if (!worldObj.isRemote) {
                TFItems.moonwormQueen.onItemUse(null, (EntityPlayer) this.getThrower(), this.worldObj, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, 0, 0, 0);
            }

        }

        if (mop.entityHit != null) {
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
        }

        for (int var3 = 0; var3 < 8; ++var3) {
            this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(TFBlocks.moonworm) + "_0", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }


}
