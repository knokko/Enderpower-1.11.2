package nl.knokko.enderpower.energy;

public interface IEnergyStorage extends IEnergy {
	
	/**
	 * @return The amount of energy that is currently stored in this storage.
	 */
	long getStoredEnergy(EnergyType type);
	
	/**
	 * @return The maximum amount of energy that can be stored in this storage.
	 */
	long getMaxEnergy(EnergyType type);
	
	/**
	 * Adds this amount of energy to this storage
	 * @param amount The amount of energy that should be stored.
	 * @return The amount of energy that could not have been added to this storage.
	 */
	long storeEnergy(EnergyType type, long amount);
	
	/**
	 * Tries to drain the requested amount of energy from this storage.
	 * @param requestedAmount The amount of energy that is requested.
	 * @return The amount of energy that has been drained.
	 */
	long drainEnergy(EnergyType type, long requestedAmount);
	
	/**
	 * Set the amount of energy of the specified type to a given value.
	 * @param type The energy type
	 * @param amount The amount
	 */
	void setEnergy(EnergyType type, long amount);
}
