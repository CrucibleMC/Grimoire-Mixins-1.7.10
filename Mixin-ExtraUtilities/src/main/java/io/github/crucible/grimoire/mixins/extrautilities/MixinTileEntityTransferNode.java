package io.github.crucible.grimoire.mixins.extrautilities;

import com.gamerforea.eventhelper.util.EventUtils;
import com.rwtema.extrautils.ExtraUtils;
import com.rwtema.extrautils.tileentity.enderquarry.IChunkLoad;
import com.rwtema.extrautils.tileentity.transfernodes.Frequency;
import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeUpgradeInventory;
import com.rwtema.extrautils.tileentity.transfernodes.TransferNodeEnderRegistry;
import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(value = TileEntityTransferNode.class, remap = false)
public abstract class MixinTileEntityTransferNode extends TileEntity implements IPipe, INode, IPipeCosmetic, IChunkLoad {

    @Shadow
    public int pipe_type = 0;
    @Shadow
    public TileEntityTransferNodeUpgradeInventory upgrades;
    @Shadow
    public boolean isReceiver = false;
    @Shadow
    public INodeBuffer buffer;
    @Shadow
    public TileEntityTransferNode.SearchType searchType;
    @Shadow
    protected int maxCoolDown;
    @Shadow
    protected int stepCoolDown;

    /**
     * @author EverNife
     * @reason Desativar os upgrades de velocidade!
     */
    @Overwrite
    public void calcUpgradeModifiers() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (this.isReceiver) {
                TransferNodeEnderRegistry.clearTileRegistrations(this.buffer);
            }

            this.isReceiver = false;
            this.stepCoolDown = 2;
            TileEntityTransferNode.SearchType prevSearchType = this.searchType;
            this.searchType = TileEntityTransferNode.SearchType.RANDOM_WALK;
            int prev_pipe_type = this.pipe_type;
            if (this.upgrades.isValidPipeType(this.pipe_type)) {
                this.pipe_type = 0;
            }

            for(int i = 0; i < this.upgrades.getSizeInventory(); ++i) {
                if (this.upgrades.getStackInSlot(i) != null && ExtraUtils.nodeUpgrade != null && this.upgrades.getStackInSlot(i).getItem() == ExtraUtils.nodeUpgrade) {
                    if (this.upgrades.getStackInSlot(i).getItemDamage() == 0) {
                        //Mixin Replace maxCooldown with a limit of 6
                        for(int k = 0; k < this.upgrades.getStackInSlot(i).stackSize && this.stepCoolDown < 2; ++k) {
                            ++this.stepCoolDown;
                        }
                    } else if (this.upgrades.getStackInSlot(i).getItemDamage() == 6 && this.upgrades.getStackInSlot(i).hasDisplayName()) {
                        TransferNodeEnderRegistry.registerTile(new Frequency(this.upgrades.getStackInSlot(i)), this.buffer);
                        this.isReceiver = true;
                    } else if (this.upgrades.getStackInSlot(i).getItemDamage() == 7) {
                        this.searchType = TileEntityTransferNode.SearchType.DEPTH_FIRST;
                    } else if (this.upgrades.getStackInSlot(i).getItemDamage() == 8) {
                        this.searchType = TileEntityTransferNode.SearchType.BREADTH_FIRST;
                    }
                } else if (this.upgrades.pipeType(this.upgrades.getStackInSlot(i)) > 0) {
                    this.pipe_type = this.upgrades.pipeType(this.upgrades.getStackInSlot(i));
                }
            }

            if (prevSearchType != this.searchType) {
                this.resetSearch();
            }

            if (prev_pipe_type != this.pipe_type) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }

        }
    }

    @Shadow
    public void resetSearch() {
    }
}
