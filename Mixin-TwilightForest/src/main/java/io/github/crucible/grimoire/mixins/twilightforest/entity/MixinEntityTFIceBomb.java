package io.github.crucible.grimoire.mixins.twilightforest.entity;

import com.gamerforea.eventhelper.util.EventUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.boss.EntityTFIceBomb;

@Mixin (value = EntityTFIceBomb.class, remap = false)
public abstract class MixinEntityTFIceBomb extends EntityThrowable {

    public MixinEntityTFIceBomb(String someRandom) {
        super(null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author Rehab_CZ
     * @reason Check player instance and fire events
     */
    @Overwrite
    private void doTerrainEffect(int x, int y, int z) {

        EntityLivingBase thrower = this.getThrower();
        if ( thrower == null || !(thrower instanceof EntityPlayerMP) || thrower instanceof FakePlayer){
            return;
        }

        EntityPlayerMP playerMP = (EntityPlayerMP) thrower;

        if (EventUtils.cantBreak(playerMP, x, y, z)){
            return;
        }

        if (this.worldObj.getBlock(x, y, z).getMaterial() == Material.water) {
            this.worldObj.setBlock(x, y, z, Blocks.ice);
        }
        if (this.worldObj.getBlock(x, y, z).getMaterial() == Material.lava) {
            this.worldObj.setBlock(x, y, z, Blocks.obsidian);
        }
        if (this.worldObj.isAirBlock(x, y, z) && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, x, y, z)) {
            this.worldObj.setBlock(x, y, z, Blocks.snow_layer);
        }
    }
}
