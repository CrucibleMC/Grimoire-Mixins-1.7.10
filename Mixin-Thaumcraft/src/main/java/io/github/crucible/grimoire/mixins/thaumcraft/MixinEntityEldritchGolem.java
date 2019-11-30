package io.github.crucible.grimoire.mixins.thaumcraft;

import com.gamerforea.eventhelper.util.EventUtils;
import io.github.crucible.grimoire.forge.core.FakePlayerManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.entities.monster.boss.EntityEldritchGolem;

@Mixin(value = EntityEldritchGolem.class, remap = false)
public abstract class MixinEntityEldritchGolem {

    @Redirect(method = "func_70636_d", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147480_a(IIIZ)Z"))
    private boolean breakBlockProxy(World world, int x, int y, int z, boolean slaOqSeriaIsso) {
        if (EventUtils.cantBreak(FakePlayerManager.get((WorldServer) world), x, y, z)) return false;
        else return world.func_147480_a(x, y, z, slaOqSeriaIsso);
    }

}
