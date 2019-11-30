package io.github.crucible.grimoire.mixins.botania;

import baubles.common.lib.PlayerHandler;
import baubles.common.network.PacketHandler;
import baubles.common.network.PacketSyncBauble;
import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.botania.api.item.ISequentialBreaker;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.ItemLokiRing;

import java.util.List;

// TODO: 17/10/2019 Fazer um fix usando só redirect.
@Mixin(value = ItemLokiRing.class, remap = false)
public abstract class MixinItemLokiRing {

    @Shadow
    private static ItemStack getLokiRing(EntityPlayer player) {
        return null;
    }

    @Shadow
    private static List<ChunkCoordinates> getCursorList(ItemStack stack) {
        return null;
    }

    @Shadow
    private static ChunkCoordinates getOriginPos(ItemStack stack) {
        return null;
    }

    @Shadow
    private static void setOriginPos(ItemStack stack, int x, int y, int z) {
    }

    @Shadow
    private static void setCursorList(ItemStack stack, List<ChunkCoordinates> cursors) {
    }

    @Shadow
    private static void addCursor(ItemStack stack, int x, int y, int z) {
    }

    /**
     * @author juanmuscaria
     * @reason Fix temporário, será trocado para redirects.
     */
    @SuppressWarnings("ConstantConditions")
    @Overwrite
    public static void breakOnAllCursors(EntityPlayer player, Item item, ItemStack stack, int x, int y, int z, int side) {
        ItemStack lokiRing = getLokiRing(player);
        if (lokiRing == null || player.worldObj.isRemote || !(item instanceof ISequentialBreaker))
            return;

        List<ChunkCoordinates> cursors = getCursorList(lokiRing);
        ISequentialBreaker breaker = (ISequentialBreaker) item;
        World world = player.worldObj;
        boolean silk = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) > 0;
        int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
        boolean dispose = breaker.disposeOfTrashBlocks(stack);

        for (ChunkCoordinates coords : cursors) {
            int xp = x + coords.posX;
            int yp = y + coords.posY;
            int zp = z + coords.posZ;
            if (EventUtils.cantBreak(player, x, y, z)) continue;
            Block block = world.getBlock(xp, yp, zp);
            breaker.breakOtherBlock(player, stack, xp, yp, zp, x, y, z, side);
            ToolCommons.removeBlockWithDrops(player, stack, player.worldObj, xp, yp, zp, x, y, z, block, new Material[]{block.getMaterial()}, silk, fortune, block.getBlockHardness(world, xp, yp, zp), dispose);
        }
    }

    /**
     * @author juanmuscaria
     * @reason Fix temporário, será trocado para redirects.
     */
    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    @Overwrite
    public void onPlayerInteract(PlayerInteractEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack lokiRing = getLokiRing(player);
        if (lokiRing == null || player.worldObj.isRemote)
            return;

        int slot = -1;
        IInventory inv2 = PlayerHandler.getPlayerBaubles(player);
        for (int i = 0; i < inv2.getSizeInventory(); i++) {
            ItemStack stack = inv2.getStackInSlot(i);
            if (stack == lokiRing) {
                slot = i;
                break;
            }
        }

        ItemStack heldItemStack = player.getCurrentEquippedItem();
        ChunkCoordinates originCoords = getOriginPos(lokiRing);
        MovingObjectPosition lookPos = ToolCommons.raytraceFromEntity(player.worldObj, player, true, 10F);
        List<ChunkCoordinates> cursors = getCursorList(lokiRing);
        int cursorCount = cursors.size();

        int cost = Math.min(cursorCount, (int) Math.pow(Math.E, cursorCount * 0.25));

        if (heldItemStack == null && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && player.isSneaking()) {
            if (originCoords.posY == -1 && lookPos != null) {
                setOriginPos(lokiRing, lookPos.blockX, lookPos.blockY, lookPos.blockZ);
                setCursorList(lokiRing, null);
                if (player instanceof EntityPlayerMP)
                    PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, slot), (EntityPlayerMP) player);
            } else if (lookPos != null) {
                if (originCoords.posX == lookPos.blockX && originCoords.posY == lookPos.blockY && originCoords.posZ == lookPos.blockZ) {
                    setOriginPos(lokiRing, 0, -1, 0);
                    if (player instanceof EntityPlayerMP)
                        PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, slot), (EntityPlayerMP) player);
                } else {
                    addCursor:
                    {
                        int relX = lookPos.blockX - originCoords.posX;
                        int relY = lookPos.blockY - originCoords.posY;
                        int relZ = lookPos.blockZ - originCoords.posZ;

                        for (ChunkCoordinates cursor : cursors)
                            if (cursor.posX == relX && cursor.posY == relY && cursor.posZ == relZ) {
                                cursors.remove(cursor);
                                setCursorList(lokiRing, cursors);
                                if (player instanceof EntityPlayerMP)
                                    PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, slot), (EntityPlayerMP) player);
                                break addCursor;
                            }

                        addCursor(lokiRing, relX, relY, relZ);
                        if (player instanceof EntityPlayerMP)
                            PacketHandler.INSTANCE.sendTo(new PacketSyncBauble(player, slot), (EntityPlayerMP) player);
                    }
                }
            }
        } else if (heldItemStack != null && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && lookPos != null && player.isSneaking()) {
            for (ChunkCoordinates cursor : cursors) {
                int x = lookPos.blockX + cursor.posX;
                int y = lookPos.blockY + cursor.posY;
                int z = lookPos.blockZ + cursor.posZ;
                if (EventUtils.cantBreak(event.entityPlayer, x, y, z)) {
                    event.setCanceled(true);
                    return;
                }
                Item item = heldItemStack.getItem();
                if (!player.worldObj.isAirBlock(x, y, z) && ManaItemHandler.requestManaExact(lokiRing, player, cost, true)) {
                    item.onItemUse(player.capabilities.isCreativeMode ? heldItemStack.copy() : heldItemStack, player, player.worldObj, x, y, z, lookPos.sideHit, (float) lookPos.hitVec.xCoord - x, (float) lookPos.hitVec.yCoord - y, (float) lookPos.hitVec.zCoord - z);
                    if (heldItemStack.stackSize == 0) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
