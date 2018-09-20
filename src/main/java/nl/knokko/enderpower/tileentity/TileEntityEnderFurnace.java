package nl.knokko.enderpower.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.BlockEnderFurnace;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;
import nl.knokko.enderpower.container.ContainerEnderFurnace;
import nl.knokko.enderpower.energy.EnergyData;
import nl.knokko.enderpower.energy.EnergyStorageSingle;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.energy.IEnergyItem;
import nl.knokko.enderpower.energy.IEnergyStorage;
import nl.knokko.enderpower.tileentity.base.TileEntityGuiBase;

public class TileEntityEnderFurnace extends TileEntityGuiBase implements IEnergyStorage {
	
	private int progress;
	
	protected EnergyStorageSingle energy;
	
	public TileEntityEnderFurnace(){
		this.energy = new EnergyStorageSingle(EnergyType.ENDER, 10000);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0;
	}

	@Override
	public int getField(int id) {
		if(id == 0)
			return progress;
		if(id == 1)
			return getBurnTime(inventory[0].getItem());
		if(id == 2)
			return (int) energy.getEnergy();
		throw new IllegalArgumentException("Unknown field id: " + id);
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0)
			progress = value;
		if(id == 2)
			energy.setEnergy(value);
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if(side == EnumFacing.DOWN)
			return new int[]{1};
		return new int[]{0};
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt = super.writeToNBT(nbt);
		nbt.setInteger("Progress", progress);
		nbt.setLong("Energy", energy.getEnergy());
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		progress = nbt.getInteger("Progress");
		energy.setEnergy(nbt.getLong("Energy"));
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player){
		return new ContainerEnderFurnace(inventory, this);
	}

	@Override
	public void update() {
		ItemStack input = inventory[0];
		if(!input.isEmpty() && energy.getEnergy() < getBurnEnergy(input.getItem()))
			requestEnergy(getBurnEnergy(input.getItem()) - energy.getEnergy());
		if(canBurn())
			continueBurning();
		else {
			progress = 0;
			if(getState())
				setState(false);
		}
		if(inventory[2].getItem() instanceof IEnergyItem){
			long requested = energy.getMaxEnergy() - energy.getEnergy();
			if(requested > 0){
				long drained = ((IEnergyItem) inventory[2].getItem()).drainEnergy(inventory[2], energy.getType(), requested);
				if(drained > 0){
					energy.storeEnergy(drained);
					world.markChunkDirty(pos, this);
				}
			}
		}
	}

	@Override
	public String getGuiID() {
		return EnderPower.MODID + ":enderfurnace";
	}
	
	private boolean canBurn(){
		ItemStack input = inventory[0];
		if(!input.isEmpty()){
			ItemStack result = getResult(input);
			ItemStack output = inventory[1];
			if(!output.isEmpty() && !result.isEmpty() && output.getItem().equals(result.getItem()) && output.getCount() < output.getMaxStackSize() && energy.getEnergy() >= getBurnEnergy(input.getItem()))
				return true;
			return !result.isEmpty() && output.isEmpty() && energy.getEnergy() >= getBurnEnergy(input.getItem());
		}
		return false;
	}
	
	private int getBurnEnergy(Item item){
		return 100;
	}
	
	private int getBurnTime(Item item){
		return 100;
	}
	
	private void startBurning(){
		if(!getState())
			setState(true);
	}
	
	private void finishBurning(){
		if(canBurn()){
			energy.drainEnergy(getBurnEnergy(inventory[0].getItem()));
			decrStackSize(0, 1);
			if(!inventory[1].isEmpty())
				inventory[1].setCount(inventory[1].getCount() + 1);
			else
				inventory[1] = getResult(inventory[0]).copy();
			progress = 0;
			markDirty();
		}
		if(!canBurn())
			setState(false);
	}
	
	private void continueBurning(){
		if(progress == 0)
			startBurning();
		progress++;
		if(progress >= getBurnTime(inventory[0].getItem()))
			finishBurning();
		markDirty();
	}
	
	protected void requestEnergy(long amount){
		energy.storeEnergy(EnergyData.drainEnergyAround(world, pos, energy.getType(), amount));
	}
	
	private ItemStack getResult(ItemStack input){
		return FurnaceRecipes.instance().getSmeltingResult(input);
	}
	
	protected void setState(boolean on){
		if(!world.isRemote)
			BlockEnderFurnace.setState(on, world, pos);
	}
	
	protected boolean getState(){
		return world.getBlockState(pos).getBlock() == EnderPowerBlocks.ENDER_FURNACE_ON;
	}

	@Override
	protected ItemStack[] createInventory() {
		return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
	}

	@Override
	public String getDefaultName() {
		return "Ender Furnace";
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

	public long getMaxEnergy() {
		return energy.getMaxEnergy();
	}

	@Override
	public void setEnergy(EnergyType type, long amount) {
		energy.setEnergy(type, amount);
	}
}
