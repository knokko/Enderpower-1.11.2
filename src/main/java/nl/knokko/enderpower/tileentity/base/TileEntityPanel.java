package nl.knokko.enderpower.tileentity.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import nl.knokko.enderpower.container.ContainerPanel;
import nl.knokko.enderpower.energy.EnergyStorageSingle;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.energy.IEnergy;
import nl.knokko.enderpower.energy.IEnergyItem;
import nl.knokko.enderpower.energy.IEnergyStorage;

public abstract class TileEntityPanel extends TileEntityGuiBase implements IEnergyStorage {
	
	private final EnergyStorageSingle energy;

	public TileEntityPanel() {
		this.energy = createStorage();
	}
	
	protected abstract EnergyStorageSingle createStorage();
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerPanel(playerInventory, this);
	}

	@Override
	public int getField(int id){
		if(id == 0)
			return (int) energy.getEnergy();
		return -1;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0)
			energy.setEnergy(value);
	}

	@Override
	public int getFieldCount() {
		return 1;
	}
	
	protected abstract boolean isPowered();
	
	protected abstract long getPower();

	@Override
	public void update() {
		if(isPowered())
			energy.storeEnergy(getPower());
		if(inventory[0].getItem() instanceof IEnergyItem){
			long requested = ((IEnergyItem) inventory[0].getItem()).getMaxEnergy(inventory[0], energy.getType()) - ((IEnergyItem) inventory[0].getItem()).getStoredEnergy(inventory[0], energy.getType());
			if(requested > 0){
				long available = energy.drainEnergy(requested);
				if(available > 0){
					long returned = ((IEnergyItem) inventory[0].getItem()).storeEnergy(inventory[0], energy.getType(), available);
					if(returned > 0)
						energy.storeEnergy(returned);
				}
				this.world.markChunkDirty(pos, this);
			}
		}
	}

	@Override
	protected ItemStack[] createInventory() {
		return new ItemStack[]{ItemStack.EMPTY};
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem() instanceof IEnergy;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 0 && stack.getItem() instanceof IEnergyItem;
	}
	
	@Override
	public long getStoredEnergy(EnergyType type) {
		return energy.getStoredEnergy(type);
	}

	@Override
	public long getMaxEnergy(EnergyType type) {
		return energy.getMaxEnergy(type);
	}
	
	public long getMaxEnergy(){
		return energy.getMaxEnergy();
	}

	@Override
	public long storeEnergy(EnergyType type, long amount) {
		return energy.storeEnergy(type, amount);
	}

	@Override
	public long drainEnergy(EnergyType type, long requestedAmount) {
		return energy.drainEnergy(type, requestedAmount);
	}
	
	@Override
	public void setEnergy(EnergyType type, long amount){
		energy.setEnergy(type, amount);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt = super.writeToNBT(nbt);
		nbt.setLong("Energy", energy.getEnergy());
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		energy.setEnergy(nbt.getLong("Energy"));
	}
}
