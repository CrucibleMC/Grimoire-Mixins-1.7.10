package io.github.crucible.grimoire.mixins.industrialcraft;

import com.gamerforea.eventhelper.util.EventUtils;
import ic2.api.event.LaserEvent;
import ic2.core.item.tool.EntityMiningLaser;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static ic2.core.item.tool.EntityMiningLaser.unmineableBlocks;

@Mixin(value = EntityMiningLaser.class, remap = false)
public abstract class MixinEntityMiningLaser extends Entity {

    public MixinEntityMiningLaser(String someString) {
        super(null);
    }

    @Shadow
    public abstract boolean takeDataFromEvent(LaserEvent aEvent);

    @Shadow public EntityLivingBase owner;

    /**
     * @author Rehab_CZ
     * @reason Minig laser fix
     */
    @Overwrite
    public boolean canMine(Block block) {

        if (EventUtils.cantBreak((EntityPlayer)this.owner,posX, posY, posZ)){
            return false;
        }

        return !unmineableBlocks.contains(block);
    }

}
