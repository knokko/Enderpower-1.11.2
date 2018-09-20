package nl.knokko.enderpower.energy;

import net.minecraft.item.ItemStack;

public interface IEnergyItem extends IEnergy {
	
	/**
	 * @return The amount of energy that is currently stored in this item.
	 */
	long getStoredEnergy(ItemStack stack, EnergyType type);
	
	/**
	 * @return The maximum amount of energy that can be stored in this item.
	 */
	long getMaxEnergy(ItemStack stack, EnergyType type);
	
	/**
	 * Adds this amount of energy to this item
	 * @param amount The amount of energy that should be stored.
	 * @return The amount of energy that could not have been added to this storage.
	 */
	long storeEnergy(ItemStack stack, EnergyType type, long amount);
	
	/**
	 * Tries to drain the requested amount of energy from this item.
	 * @param requestedAmount The amount of energy that is requested.
	 * @return The amount of energy that has been drained.
	 */
	long drainEnergy(ItemStack stack, EnergyType type, long requestedAmount);

}
