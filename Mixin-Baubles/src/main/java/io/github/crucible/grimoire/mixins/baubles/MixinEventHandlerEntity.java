package io.github.crucible.grimoire.mixins.baubles;

import baubles.common.container.InventoryBaubles;
import baubles.common.event.EventHandlerEntity;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

//Refer https://github.com/Azanor/Baubles/blob/1.7.10/src/main/java/baubles/common/event/EventHandlerEntity.java
@Mixin(value = EventHandlerEntity.class, remap = false)
public abstract class MixinEventHandlerEntity {

    /**
     * @author EverNife
     * @reason Adiciona uma checagem extra para itens com soulbound não serem dropados do baubles na morte!
     */
    @Overwrite
    @SubscribeEvent
    public void playerDeath(PlayerDropsEvent event) {
        if (event.entity instanceof EntityPlayer
                && !event.entity.worldObj.isRemote
                && !event.entity.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            InventoryBaubles inventoryBaubles = PlayerHandler.getPlayerBaubles(event.entityPlayer);

            //Edit the function used  "inventoryBaubles.dropItemsAt()"
            Entity e = event.entityPlayer;
            for (int i = 0; i < 4; ++i) {
                if (inventoryBaubles.stackList[i] != null) {
                    if (EnchantmentHelper.getEnchantmentLevel(8, inventoryBaubles.stackList[i]) > 0){ // EnchantID 8 is the EnderIO SoulBound ID
                        continue;
                    }
                    EntityItem ei = new EntityItem(e.worldObj,
                            e.posX, e.posY + e.getEyeHeight(), e.posZ,
                            inventoryBaubles.stackList[i].copy());
                    ei.delayBeforeCanPickup = 40;
                    float f1 = e.worldObj.rand.nextFloat() * 0.5F;
                    float f2 = e.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
                    ei.motionX = -MathHelper.sin(f2) * f1;
                    ei.motionZ = MathHelper.cos(f2) * f1;
                    ei.motionY = 0.20000000298023224D;
                    event.drops.add(ei);
                    inventoryBaubles.stackList[i] = null;
                    inventoryBaubles.syncSlotToClients(i);
                }
            }
        }

    }

}