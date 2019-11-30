package io.github.crucible.grimoire.mixins.thaumcraft;

import com.gamerforea.eventhelper.util.EventUtils;
import io.github.crucible.grimoire.forge.core.FakePlayerManager;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.entities.monster.boss.EntityCultistPortal;

@Mixin(value = EntityCultistPortal.class, remap = false)
public abstract class MixinEntityCultistPortal {

    @Redirect(method = "func_70071_h_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147465_d(IIILnet/minecraft/block/Block;II)Z"))
    private boolean setBlockProxy(World world, int x, int y, int z, Block block, int i1, int i2) {
        if (EventUtils.cantBreak(FakePlayerManager.get((WorldServer) world), x, y, z) || world.getTileEntity(x, y, z) != null)
            return false;
        else return world.setBlock(x, y, z, block, i1, i2);
    }
}
