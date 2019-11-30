package io.github.crucible.grimoire.mixins.forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@SuppressWarnings("ConstantConditions")
@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract Item getItem();

    /**
     * @author juanmuscaria
     */
    @SideOnly(Side.CLIENT)
    @Debug(print = true)
    @Overwrite
    public IIcon getIconIndex() {
        try {
            Object self = this;
            return this.getItem().getIconIndex((ItemStack) self);
        } catch (Exception e) {
            return ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
        }
    }
}
