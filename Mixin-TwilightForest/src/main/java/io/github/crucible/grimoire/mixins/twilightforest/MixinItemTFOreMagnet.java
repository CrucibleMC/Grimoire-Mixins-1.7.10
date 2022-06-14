package io.github.crucible.grimoire.mixins.twilightforest;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.item.ItemTF;
import twilightforest.item.ItemTFOreMagnet;

@Mixin(value = ItemTFOreMagnet.class, remap = false)
public abstract class MixinItemTFOreMagnet extends ItemTF {

    /**
     * @author Rehab_CZ
     * @reason Fire events before magnet
     */
    @Overwrite
    protected int doMagnet(World world, EntityPlayer player, float yawOffset, float pitchOffset) {

        double range = 32.0D;

        Vec3 srcVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 lookVec = getOffsetLook(player, yawOffset, pitchOffset);
        Vec3 destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);

        int useX = MathHelper.floor_double(srcVec.xCoord);
        int useY = MathHelper.floor_double(srcVec.yCoord);
        int useZ = MathHelper.floor_double(srcVec.zCoord);

        int destX = MathHelper.floor_double(destVec.xCoord);
        int destY = MathHelper.floor_double(destVec.yCoord);
        int destZ = MathHelper.floor_double(destVec.zCoord);

        if (EventUtils.cantBreak(player, useX, useY, useZ)
                || EventUtils.cantBreak(player, destX, destY, destZ)){
            return 1; //Needs to return 1 to stop the process!
        }

        return doMagnet(world, useX, useY, useZ, destX, destY, destZ);
    }

    @Shadow
    public static int doMagnet(World world, int useX, int useY, int useZ, int destX, int destY, int destZ) {
        return 0;
    }

    @Shadow
    protected abstract Vec3 getOffsetLook(EntityPlayer player, float yawOffset, float pitchOffset);

}
