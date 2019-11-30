package io.github.crucible.grimoire.mixins.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.blocks.BlockTable;

@Mixin(value = BlockTable.class, remap = false)
public abstract class MixinBlockTable {

    @Inject(method = "func_149727_a", at = @At(value = "INVOKE"), cancellable = true)
    private void verifyOpenContainer(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are, CallbackInfoReturnable<Boolean> ci) {
        if (player instanceof FakePlayer) return;
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.closeScreen();
        } else
            throw new IllegalArgumentException("Foi passado um objeto que não é um jogador:" + player.getClass().getName());
    }

}