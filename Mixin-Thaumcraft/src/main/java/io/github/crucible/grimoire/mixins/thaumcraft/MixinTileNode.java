package io.github.crucible.grimoire.mixins.thaumcraft;

import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.tiles.TileNode;

@Mixin(value = TileNode.class, remap = false)
public abstract class MixinTileNode extends TileThaumcraft implements INode, IWandable {

    @Shadow
    int count;

    @Shadow
    public abstract void nodeChange();

    @Inject(method = "handleTaintNode", at = @At("HEAD"), cancellable = true)
    private void diableGriefingTaintNode(boolean change, CallbackInfoReturnable<Boolean> ci) {
        if (this.getNodeType() != NodeType.PURE && this.getNodeType() != NodeType.TAINTED && this.count % 100 == 0) {
            BiomeGenBase bg = this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord);
            if (bg.biomeID == ThaumcraftWorldGenerator.biomeTaint.biomeID && this.worldObj.rand.nextInt(500) == 0) {
                this.setNodeType(NodeType.TAINTED);
                this.nodeChange();
            }
        }
        ci.setReturnValue(change);
        ci.cancel();
    }

    @Inject(method = "handleHungryNodeSecond", at = @At("HEAD"), cancellable = true)
    private void diableGriefingHungryNode(boolean change, CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(change);
        ci.cancel();
    }
}
