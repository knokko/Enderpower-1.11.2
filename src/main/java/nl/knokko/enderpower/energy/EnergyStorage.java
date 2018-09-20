package nl.knokko.enderpower.energy;

public abstract class EnergyStorage implements IEnergyStorage {
	
	private final long[] energy;

	public EnergyStorage(long thau, long sie, long fie, long gee, long dou, long ender) {
		this.energy = new long[]{thau, sie, fie, gee, dou, ender};
	}

	@Override
	public long getStoredEnergy(EnergyType type) {
		return energy[type.ordinal()];
	}

	@Override
	public long storeEnergy(EnergyType type, long amount) {
		if(amount + energy[type.ordinal()] >= getMaxEnergy(type)){
			energy[type.ordinal()] = getMaxEnergy(type);
			return (energy[type.ordinal()] - getMaxEnergy(type)) + amount;
		}
		energy[type.ordinal()] += amount;
		return 0;
	}

	@Override
	public long drainEnergy(EnergyType type, long requestedAmount) {
		if(energy[type.ordinal()] >= requestedAmount){
			energy[type.ordinal()] -= requestedAmount;
			return requestedAmount;
		}
		requestedAmount = energy[type.ordinal()];
		energy[type.ordinal()] = 0;
		return requestedAmount;
	}
	
	@Override
	public void setEnergy(EnergyType type, long amount){
		energy[type.ordinal()] = amount;
		if(energy[type.ordinal()] > getMaxEnergy(type))
			energy[type.ordinal()] = getMaxEnergy(type);
	}
}
