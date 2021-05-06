package io.github.crucible.grimoire.mixins.draconicevolution;

import com.brandon3055.draconicevolution.common.entity.EntityCustomDragon;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Method;

//Faz o dragão disparar o evento de criação do portal.
@Mixin(value = EntityCustomDragon.class, remap = false)
public abstract class MixinEntityCustomDragon extends EntityDragon {

    @Shadow
    public int portalX = 0;
    @Shadow
    public int portalY = 67;
    @Shadow
    public int portalZ = 0;
    @Shadow
    protected boolean isUber = false;
    @Shadow
    private final boolean createPortal = true;

    public MixinEntityCustomDragon(World p_i1700_1_) {
        super(p_i1700_1_);
    }

    /**
     * @author juanmuscaria
     * @reason Fazer o Ender Dragon do draconic disparar o mesmo evento do dragão normal.
     */
    @Overwrite
    private void spawnEgg() {
        if (ConfigHandler.dragonEggSpawnLocation[0] != 0 || ConfigHandler.dragonEggSpawnLocation[1] != 0 || ConfigHandler.dragonEggSpawnLocation[1] != 0 && !isUber) {
            portalX = ConfigHandler.dragonEggSpawnLocation[0];
            portalY = ConfigHandler.dragonEggSpawnLocation[1];
            portalZ = ConfigHandler.dragonEggSpawnLocation[2];
        }

        BlockEndPortal.field_149948_a = true;

        if (createPortal || isUber) {
            createPortal(portalX, portalZ);
        }
        LogHelper.info("spawn egg");
        if (worldObj.getBlock(portalX, portalY + 1, portalZ) == Blocks.air) {
            worldObj.setBlock(portalX, portalY + 1, portalZ, Blocks.dragon_egg);
            LogHelper.info("spawn egg2 " + portalX + " " + portalY + " " + portalZ);
        } else {
            for (int i = portalY + 1; i < 250; i++) {
                if (worldObj.getBlock(portalX, i, portalZ) == Blocks.air) {
                    worldObj.setBlock(portalX, i, portalZ, Blocks.dragon_egg);
                    LogHelper.info("spawn egg3");
                    break;
                }
            }
        }

        for (int iX = portalX - 2; iX <= portalX + 2; iX++) {
            for (int iZ = portalZ - 2; iZ <= portalZ + 2; iZ++) {

                if (worldObj.getBlock(iX, portalY - 4, iZ) == Blocks.bedrock && !(iX == portalX && iZ == portalZ)) {
                    worldObj.setBlock(iX, portalY - 3, iZ, Blocks.end_portal);
                }
            }
        }


        worldObj.setBlock(portalX - 1, portalY - 1, portalZ, Blocks.torch);
        worldObj.setBlock(portalX + 1, portalY - 1, portalZ, Blocks.torch);
        worldObj.setBlock(portalX, portalY - 1, portalZ - 1, Blocks.torch);
        worldObj.setBlock(portalX, portalY - 1, portalZ + 1, Blocks.torch);


        BlockEndPortal.field_149948_a = false;

    }

    private void createPortal(int portalX, int portalZ) {
        try {
            @SuppressWarnings("JavaReflectionMemberAccess") //Existe no runtime, bruxaria do thermos.
                    Method method = EntityDragon.class.getMethod("func_70975_a", int.class, int.class);
            method.invoke(this, portalX, portalZ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
