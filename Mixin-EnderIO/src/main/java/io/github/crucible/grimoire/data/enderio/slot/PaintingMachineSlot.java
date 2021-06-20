package io.github.crucible.grimoire.data.enderio.slot;

import crazypants.enderio.machine.gui.AbstractMachineContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PaintingMachineSlot extends Slot {

    protected final AbstractMachineContainer machineContainer;

    protected PaintingMachineSlot(AbstractMachineContainer machineContainer, int slot, int xDisplayPosition, int yDisplayPosition) {
        super(machineContainer.getInv(), slot, xDisplayPosition, xDisplayPosition);
        this.machineContainer = machineContainer;
    }

    public static class DISPLAY extends PaintingMachineSlot{
        public DISPLAY(AbstractMachineContainer machineContainer, int slot, int xDisplayPosition, int yDisplayPosition) {
            super(machineContainer, slot, xDisplayPosition, yDisplayPosition);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return false;
        }
    }

    public static class INPUT extends PaintingMachineSlot{
        public INPUT(AbstractMachineContainer machineContainer, int slot, int xDisplayPosition, int yDisplayPosition) {
            super(machineContainer, slot, xDisplayPosition, yDisplayPosition);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return !itemStack.hasTagCompound() && machineContainer.getInv().isItemValidForSlot(this.getSlotIndex(), itemStack);
        }
    }
}
