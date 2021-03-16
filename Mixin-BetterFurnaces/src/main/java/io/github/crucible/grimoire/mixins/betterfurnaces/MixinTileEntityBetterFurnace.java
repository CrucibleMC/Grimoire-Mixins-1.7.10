package io.github.crucible.grimoire.mixins.betterfurnaces;

import at.flabs.betterfurnaces.BetterFurnaces;
import at.flabs.betterfurnaces.core.TileEntityBetterFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;


@Mixin(value = TileEntityBetterFurnace.class, remap = false)
public abstract class MixinTileEntityBetterFurnace {

    @Shadow private byte facing;

    @Shadow public boolean storage;

    @Shadow public abstract ItemStack getUpgrade(Item upgrade);

    @Shadow public abstract byte getAbsoluteSide(byte relative, byte standart);

    /**
     * @author EverNife
     *
     * @reason I hate spam :D
     *
     * If you have a console spam like this:
     *
     * [4] 0, 1, 5
     * [1] 0, 1, 5
     * [4, 5] 0, 3, 1
     * [4, 5] 0, 3, 1
     *
     * Like spaming over 10 times a second, well, this override fix it '-'
     */
    @Overwrite
    public int[] func_94128_d(int side) {
        if (side == this.facing) {
            return new int[]{6, 7, 8};
        } else {
            ItemStack is = this.getUpgrade(BetterFurnaces.instance.upg.itemPiping);
            if (is != null) {
                byte out = this.getAbsoluteSide(BetterFurnaces.instance.upg.itemPiping.getSide("Out", is), (byte)0);
                byte in = this.getAbsoluteSide(BetterFurnaces.instance.upg.itemPiping.getSide("In", is), (byte)1);
                byte fin = this.getAbsoluteSide(BetterFurnaces.instance.upg.itemPiping.getSide("Fin", is), (byte)1);
                int[] res = new int[0];
                if (side == out) {
                    res = Arrays.copyOf(res, res.length + (this.storage ? 2 : 1));
                    if (this.storage) {
                        res[res.length - 2] = 4;
                    }

                    res[res.length - 1] = this.storage ? 5 : 4;
                }

                if (side == in) {
                    res = Arrays.copyOf(res, res.length + (this.storage ? 2 : 1));
                    if (this.storage) {
                        res[res.length - 2] = 1;
                    }

                    res[res.length - 1] = this.storage ? 0 : 1;
                }

                if (side == fin) {
                    res = Arrays.copyOf(res, res.length + (this.storage ? 2 : 1));
                    if (this.storage) {
                        res[res.length - 2] = 3;
                    }

                    res[res.length - 1] = this.storage ? 2 : 3;
                }

                //GRIMOIRE START
                //System.out.println(Arrays.toString(res) + " " + out + ", " + in + ", " + fin); //Comment this line!
                //GRIMOIRE END

                return res;
            } else if (side == 1) {
                return this.storage ? new int[]{1, 0} : new int[]{1};
            } else if (side == 0) {
                return this.storage ? new int[]{4, 5} : new int[]{4};
            } else {
                return this.storage ? new int[]{3, 2} : new int[]{3};
            }
        }
    }
}
