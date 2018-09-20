package nl.knokko.enderpower.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import nl.knokko.enderpower.energy.IEnergyItem;

public class SlotEnergyItem extends Slot {

	public SlotEnergyItem(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return stack.getItem() instanceof IEnergyItem;
	}
}
