package io.github.crucible.grimoire.mixins.mekanism;

import mekanism.api.util.StackUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// TODO: 17/10/2019 Verificar se está funcionando.
@Mixin(value = StackUtils.class, remap = false)
public abstract class MixinStackUtils {

    /**
     * @author juanmuscaria
     * @reason Corrigir a verificação da ItemStack.
     */
    @Overwrite
    public static boolean diffIgnoreNull(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) {
            return false;
        }

        return stack1.getItem() != stack2.getItem() || stack1.getItemDamage() != stack2.getItemDamage() || stack1.getTagCompound() != stack2.getTagCompound();
    }

    /**
     * @author juanmuscaria
     * @reason Corrigir a verificação da ItemStack.
     */
    @Overwrite
    public static boolean equalsWildcard(ItemStack wild, ItemStack check) {
        if (wild != null && check != null) {
            if (!wild.isStackable() || !check.isStackable()) return false;
            return wild.getItem() == check.getItem() && (wild.getItemDamage() == OreDictionary.WILDCARD_VALUE || wild.getItemDamage() == check.getItemDamage());
        } else
            return check == wild;
    }


}