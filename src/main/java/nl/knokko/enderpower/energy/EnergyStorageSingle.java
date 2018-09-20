package nl.knokko.enderpower.energy;

public class EnergyStorageSingle implements IEnergyStorage {
	
	private EnergyType type;
	
	private final long maxEnergy;
	private long energy;

	public EnergyStorageSingle(EnergyType type, long maxEnergy) {
		this.type = type;
		this.maxEnergy = maxEnergy;
	}
	
	public void setEnergy(long value){
		energy = value;
	}

	@Override
	public long getStoredEnergy(EnergyType type) {
		if(type == this.type)
			return energy;
		return 0;
	}

	@Override
	public long getMaxEnergy(EnergyType type) {
		if(type == this.type)
			return maxEnergy;
		return 0;
	}

	@Override
	public long storeEnergy(EnergyType type, long amount) {
		if(type == this.type)
			return storeEnergy(amount);
		return amount;
	}

	@Override
	public long drainEnergy(EnergyType type, long requestedAmount) {
		if(type == this.type)
			return drainEnergy(requestedAmount);
		return 0;
	}
	
	public long getEnergy(){
		return energy;
	}
	
	public long getMaxEnergy(){
		return maxEnergy;
	}
	
	public long storeEnergy(long amount){
		if(amount + energy >= maxEnergy){
			energy = maxEnergy;
			return (energy - maxEnergy) + amount;
		}
		energy += amount;
		return 0;
	}
	
	public long drainEnergy(long requestedAmount){
		if(energy >= requestedAmount){
			energy -= requestedAmount;
			return requestedAmount;
		}
		requestedAmount = energy;
		energy = 0;
		return requestedAmount;
	}
	
	public EnergyType getType(){
		return type;
	}
	
	public void setType(EnergyType type){
		this.type = type;
	}

	@Override
	public void setEnergy(EnergyType type, long amount) {
		energy = Math.min(amount, maxEnergy);
	}
}
