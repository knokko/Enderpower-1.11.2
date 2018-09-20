package nl.knokko.enderpower.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import nl.knokko.enderpower.recipes.GeneratorRecipes;

public class SlotGeneratorFuel extends Slot {

	public SlotGeneratorFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return GeneratorRecipes.isFuel(stack.getItem());
	}
}
