package nl.knokko.enderpower.energy;

public class EnergyStorageBase extends EnergyStorage {
	
	private final long[] maxEnergy;

	public EnergyStorageBase(long[] maxEnergy, long thau, long sie, long fie, long gee, long dou, long ender) {
		super(thau, sie, fie, gee, dou, ender);
		this.maxEnergy = maxEnergy;
	}

	@Override
	public long getMaxEnergy(EnergyType type) {
		return maxEnergy[type.ordinal()];
	}

}
