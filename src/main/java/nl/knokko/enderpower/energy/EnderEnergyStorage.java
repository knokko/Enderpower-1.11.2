package nl.knokko.enderpower.energy;

public class EnderEnergyStorage extends EnergyStorage {
	
	private EnergyData data;
	
	public EnderEnergyStorage(EnergyData data){
		super(0, 0, 0, 0, 0, 0);
		this.data = data;
	}

	public EnderEnergyStorage(long thau, long sie, long fie, long gee, long dou, long ender) {
		super(thau, sie, fie, gee, dou, ender);
	}

	@Override
	public long getMaxEnergy(EnergyType type) {
		return Long.MAX_VALUE;
	}
	
	@Override
	public long drainEnergy(EnergyType type, long amount){
		data.markDirty();
		return super.drainEnergy(type, amount);
	}
	
	@Override
	public long storeEnergy(EnergyType type, long amount){
		data.markDirty();
		return super.storeEnergy(type, amount);
	}
}
