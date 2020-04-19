package io.github.crucible.grimoire.mixins.armourersworkshop;

import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import riskyken.armourersWorkshop.ArmourersWorkshop;
import riskyken.armourersWorkshop.common.blocks.BlockMannequin;
import riskyken.armourersWorkshop.common.items.ModItems;
import riskyken.armourersWorkshop.common.tileentities.TileEntityMannequin;

@Pseudo
@Mixin(value = BlockMannequin.class)
public abstract class MixinBlockMannequin {

    /**
     * @author EverNife
     * @reason Fire a break-event before opening a manequin!
     */
    @Overwrite(remap = true)
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xHit, float yHit, float zHit) {
        if (!player.canPlayerEdit(x, y, z, side, player.getCurrentEquippedItem())) {
            return false;
        } else {
            if (!world.isRemote) {
                //Mixin Start
                if (EventUtils.cantBreak(player,x,y,z)){
                    return false;
                }
                //Mixin End
                if (player.inventory.getCurrentItem() != null) {
                    if (player.inventory.getCurrentItem().getItem() == ModItems.mannequinTool) {
                        return false;
                    }

                    if (player.inventory.getCurrentItem().getItem() == ModItems.paintbrush) {
                        return false;
                    }
                }

                int meta = world.getBlockMetadata(x, y, z);
                int yOffset = 0;
                if (meta == 1) {
                    yOffset = -1;
                }

                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() == Items.name_tag) {
                    TileEntity te = world.getTileEntity(x, y + yOffset, z);
                    if (te instanceof TileEntityMannequin && stack.getItem() == Items.name_tag) {
                        ((TileEntityMannequin)te).setOwner(player.getCurrentEquippedItem());
                    }
                } else {
                    FMLNetworkHandler.openGui(player, ArmourersWorkshop.instance, 6, world, x, y + yOffset, z);
                }
            }

            return player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().getItem() != ModItems.mannequinTool;
        }
    }
}
