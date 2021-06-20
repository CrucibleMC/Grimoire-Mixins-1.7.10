package io.github.crucible.grimoire.mixins.enderio;

import crazypants.enderio.machine.gui.AbstractMachineContainer;
import crazypants.enderio.machine.painter.PainterContainer;
import crazypants.enderio.machine.painter.TileEntityPainter;
import io.github.crucible.grimoire.data.enderio.slot.PaintingMachineSlot;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PainterContainer.class, remap = false)
public abstract class MixinPainterContainer extends AbstractMachineContainer<TileEntityPainter> {

    private MixinPainterContainer() {
        super(null, null);
        throw new RuntimeException("This should neve Run!");
    }

    /**
     * @author evernife
     *
     * Fix a dupe by not allowing any item that has any NBT
     * to be used as a template for the facede
     */
    @Overwrite
    protected void addMachineSlots(InventoryPlayer playerInv) {
        addSlotToContainer(new PaintingMachineSlot.INPUT(this, 0, 67, 34));
        addSlotToContainer(new PaintingMachineSlot.INPUT(this, 1, 38, 34));
        addSlotToContainer(new PaintingMachineSlot.DISPLAY(this, 2, 121, 34));
    }
}
