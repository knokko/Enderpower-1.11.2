package nl.knokko.enderpower.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.ContainerGenerator;
import nl.knokko.enderpower.energy.EnergyStorageBase;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.energy.IEnergyItem;
import nl.knokko.enderpower.energy.IEnergyStorage;
import nl.knokko.enderpower.recipes.GeneratorRecipes;
import nl.knokko.enderpower.recipes.GeneratorRecipes.Output;
import nl.knokko.enderpower.tileentity.base.TileEntityGuiBase;

public class TileEntityGenerator extends TileEntityGuiBase implements IEnergyStorage {
	
	private IEnergyStorage energy;
	
	private EnergyType burnType;
	private char burnTime;
	private char fullBurnTime;
	
	public TileEntityGenerator(){
		energy = new EnergyStorageBase(new long[]{10000, 10000, 10000, 10000, 10000, 10000}, 0, 0, 0, 0, 0, 0);
	}

	@Override
	public int getField(int id) {
		if(id == 0)
			return burnTime;
		if(id >= 1 && id <= 6)
			return (int) energy.getStoredEnergy(EnergyType.values()[id - 1]);
		if(id == 7)
			return fullBurnTime;
		if(id == 8)
			return burnType != null ? burnType.ordinal() : -1;
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0)
			burnTime = (char) value;
		else if(id >= 1 && id <= 6)
			energy.setEnergy(EnergyType.values()[id - 1], value);
		else if(id == 7)
			fullBurnTime = (char) value;
		else if(id == 8)
			burnType = value >= 0 ? EnergyType.values()[value] : null;
		else
			throw new IllegalArgumentException("Invalid id: " + id);
	}

	@Override
	public int getFieldCount() {
		return 9;
	}

	@Override
	public void update() {
		if(!world.isRemote){
			System.out.println("update on server side");
			if(isBurning())
				continueBurning();
			if(!isBurning() && canBurn())
				startBurning();
			System.out.println("energy item = " + inventory[1].getItem());
			if(inventory[1].getItem() instanceof IEnergyItem){
				IEnergyItem ie = (IEnergyItem) inventory[1].getItem();
				for(EnergyType type : EnergyType.values()){
					long requested = ie.getMaxEnergy(inventory[1], type) - ie.getStoredEnergy(inventory[1], type);
					System.out.println("requested = " + requested);
					if(requested > 0){
						long available = energy.drainEnergy(type, requested);
						System.out.println("available = " + available);
						if(available > 0){
							long returned = ie.storeEnergy(inventory[1], type, available);
							if(returned > 0)
								energy.storeEnergy(type, returned);
						}
						this.world.markChunkDirty(pos, this);
					}
				}
			}
		}
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerGenerator(playerInventory, this);
	}

	@Override
	public String getGuiID() {
		return EnderPower.MODID + ":generator";
	}

	@Override
	protected ItemStack[] createInventory() {
		return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};
	}

	@Override
	public String getDefaultName() {
		return "Generator";
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 0)
			return GeneratorRecipes.isFuel(stack.getItem());
		if(index == 1)
			return stack.getItem() instanceof IEnergyItem;
		throw new IllegalArgumentException("Invalid slot index: " + index);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0, 1};
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public long getStoredEnergy(EnergyType type) {
		return energy.getStoredEnergy(type);
	}

	@Override
	public long getMaxEnergy(EnergyType type) {
		return energy.getMaxEnergy(type);
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
	public void setEnergy(EnergyType type, long amount) {
		energy.setEnergy(type, amount);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt = super.writeToNBT(nbt);
		nbt.setShort("BurnTime", (short)(burnTime + Short.MIN_VALUE));
		nbt.setShort("FullBurnTime", (short)(fullBurnTime + Short.MIN_VALUE));
		if(burnType != null)
			nbt.setByte("BurnType", (byte) burnType.ordinal());
		nbt.setShort("ThauEnergy", (short) energy.getStoredEnergy(EnergyType.THAU));
		nbt.setShort("SieEnergy", (short) energy.getStoredEnergy(EnergyType.SIE));
		nbt.setShort("FieEnergy", (short) energy.getStoredEnergy(EnergyType.FIE));
		nbt.setShort("GeeEnergy", (short) energy.getStoredEnergy(EnergyType.GEE));
		nbt.setShort("DouEnergy", (short) energy.getStoredEnergy(EnergyType.DOU));
		nbt.setShort("EnderEnergy", (short) energy.getStoredEnergy(EnergyType.ENDER));
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		burnTime = (char) (nbt.getShort("BurnTime") - Short.MIN_VALUE);
		if(nbt.hasKey("BurnType"))
			burnType = EnergyType.values()[nbt.getByte("BurnType")];
		fullBurnTime = (char) (nbt.getShort("FullBurnTime") - Short.MIN_VALUE);
		energy.setEnergy(EnergyType.THAU, nbt.getShort("ThauEnergy"));
		energy.setEnergy(EnergyType.SIE, nbt.getShort("SieEnergy"));
		energy.setEnergy(EnergyType.FIE, nbt.getShort("FieEnergy"));
		energy.setEnergy(EnergyType.GEE, nbt.getShort("GeeEnergy"));
		energy.setEnergy(EnergyType.DOU, nbt.getShort("DouEnergy"));
		energy.setEnergy(EnergyType.ENDER, nbt.getShort("EnderEnergy"));
	}
	
	public EnergyType getBurnType(){
		return burnType;
	}
	
	private boolean canBurn(){
		Output out = GeneratorRecipes.getFuelOutput(inventory[0].getItem());
		return out != null && energy.getMaxEnergy(out.getType()) > energy.getStoredEnergy(out.getType());
	}
	
	private boolean isBurning(){
		return burnTime > 0;
	}
	
	private void startBurning(){
		Output output = GeneratorRecipes.getFuelOutput(inventory[0].getItem());
		burnTime = output.getDuration();
		burnType = output.getType();
		fullBurnTime = burnTime;
		decrStackSize(0, 1);
	}
	
	private void continueBurning(){
		burnTime--;
		energy.storeEnergy(burnType, 5);
		if(burnTime == 0)
			stopBurning();
	}
	
	private void stopBurning(){
		burnType = null;
		fullBurnTime = 1;
	}
}
