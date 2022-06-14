package io.github.crucible.grimoire.mixins.twilightforest;

import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.entity.EntityTFLoyalZombie;
import twilightforest.item.ItemTF;
import twilightforest.item.ItemTFZombieWand;

@Mixin(value = ItemTFZombieWand.class, remap = false)
public abstract class MixinItemTFZombieWand extends ItemTF {

    @Shadow
    protected abstract MovingObjectPosition getPlayerPointVec(World worldObj, EntityPlayer player, float range);

    /**
     * @author Rehab_CZ
     * @reason Fire events before spawning loyal zombie
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player) {

        if (par1ItemStack.getItemDamage() < this.getMaxDamage()) {
            player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

            if (!worldObj.isRemote) {
                MovingObjectPosition mop = getPlayerPointVec(worldObj, player, 20.0F);

                if (mop != null && !EventUtils.cantBreak(player, mop.blockX, mop.blockY, mop.blockZ)) {

                    EntityTFLoyalZombie zombie = new EntityTFLoyalZombie(worldObj);
                    zombie.setPositionAndRotation(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 1.0F, 1.0F);  /// NPE here needs to be fixed
                    zombie.setTamed(true);
                    try {
                        zombie.func_152115_b(player.getUniqueID().toString());
                    } catch (NoSuchMethodError ex) {
                        FMLLog.warning("[TwilightForest] Could not determine player name for loyal zombie, ignoring error.");
                    }
                    zombie.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1200, 1));
                    worldObj.spawnEntityInWorld(zombie);

                    par1ItemStack.damageItem(1, player);
                }
            }
        } else {
            player.stopUsingItem();
        }

        return par1ItemStack;
    }
}
