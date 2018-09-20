package nl.knokko.enderpower.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.energy.IEnergy;
import nl.knokko.enderpower.energy.IEnergyItem;

public class ItemEnergy extends ItemParam implements IEnergyItem {
	
	private EnergyType type;

	public ItemEnergy(String name, EnergyType energyType, int maxEnergy) {
		super(name);
		this.setMaxDamage(maxEnergy);
		type = energyType;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player){
		super.onCreated(stack, world, player);
		stack.setItemDamage(stack.getMaxDamage());
	}

	@Override
	public long getStoredEnergy(ItemStack stack, EnergyType type) {
		if(type == getType())
			return stack.getMaxDamage() - stack.getItemDamage();
		return 0;
	}
	
	protected void setStoredEnergy(ItemStack stack, int value){
		stack.setItemDamage(stack.getMaxDamage() - value);
	}
	
	protected void addEnergy(ItemStack stack, int amount){
		stack.setItemDamage(stack.getItemDamage() - amount);
	}

	@Override
	public long getMaxEnergy(ItemStack stack, EnergyType type) {
		if(type == getType())
			return stack.getMaxDamage();
		return 0;
	}

	@Override
	public long storeEnergy(ItemStack stack, EnergyType type, long amount) {
		if(type == getType()){
			if(amount + getStoredEnergy(stack, type) >= getMaxEnergy(stack, type)){
				setStoredEnergy(stack, (int) getMaxEnergy(stack, type));
				return getStoredEnergy(stack, type) - getMaxEnergy(stack, type) + amount;
			}
			addEnergy(stack, (int) amount);
			return 0;
		}
		return amount;
	}

	@Override
	public long drainEnergy(ItemStack stack, EnergyType type, long requestedAmount) {
		if(type == getType()){
			if(getStoredEnergy(stack, type) >= requestedAmount){
				addEnergy(stack, (int) -requestedAmount);
				return requestedAmount;
			}
			requestedAmount = getStoredEnergy(stack, type);
			setStoredEnergy(stack, 0);
			return requestedAmount;
		}
		return 0;
	}
	
	public EnergyType getType(){
		return type;
	}
}
