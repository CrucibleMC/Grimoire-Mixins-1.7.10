package io.github.crucible.grimoire.mixins.twilightforest;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.item.ItemTF;
import twilightforest.item.ItemTFTransformPowder;

@Mixin(value = ItemTFTransformPowder.class, remap = false)
public abstract class MixinItemTFTransformPowder extends ItemTF {

    @Shadow
    public abstract Class<? extends EntityLivingBase> getMonsterTransform(Class<? extends EntityLivingBase> originalMonster);

    /**
     * @author Rehab_CZ
     * @reason Fire events before transforming animal
     */
    @Overwrite
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase target) {
        Class<?> transformClass = getMonsterTransform(target.getClass());

        if (EventUtils.cantBreak(par2EntityPlayer, target.posX, target.posY, target.posZ)) {
            return true;
        }

        if (transformClass != null) {
            if (target.worldObj.isRemote) {
                if (target instanceof EntityLiving) {
                    ((EntityLiving) target).spawnExplosionParticle();
                    ((EntityLiving) target).spawnExplosionParticle();
                }
                target.worldObj.playSound(target.posX + 0.5D, target.posY + 0.5D, target.posZ + 0.5D, "mob.zombie.remedy", 1.0F + itemRand.nextFloat(), itemRand.nextFloat() * 0.7F + 0.3F, false);
            } else {
                EntityLivingBase newMonster = null;
                try {
                    newMonster = (EntityLivingBase) transformClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{target.worldObj});
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (newMonster == null) {
                    return false;
                } else {
                    newMonster.setPositionAndRotation(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                    target.worldObj.spawnEntityInWorld(newMonster);
                    target.setDead();
                }
            }
            --par1ItemStack.stackSize;
            return true;
        } else {
            return false;
        }
    }

}
