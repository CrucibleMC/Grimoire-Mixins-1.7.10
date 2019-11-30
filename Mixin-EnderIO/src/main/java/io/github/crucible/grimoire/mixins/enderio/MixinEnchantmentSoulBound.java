package io.github.crucible.grimoire.mixins.enderio;

import crazypants.enderio.enchantment.EnchantmentSoulBound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: 17/10/2019 Remover esse mixin e fazer um check na bigorna.
@Mixin(EnchantmentSoulBound.class)
public abstract class MixinEnchantmentSoulBound extends Enchantment {
    private MixinEnchantmentSoulBound() {
        super(1, 1, EnumEnchantmentType.breakable);
    }


    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(int id, CallbackInfo callbackInfo) {
        this.type = EnumEnchantmentType.breakable;
    }

}
