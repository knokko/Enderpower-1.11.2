package nl.knokko.enderpower.tileentity.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.BlockEnderFurnace;
import nl.knokko.enderpower.container.ContainerEnderFurnace;
import nl.knokko.enderpower.energy.IEnergyStorage;

public abstract class TileEntityGuiBase extends TileEntity implements ISidedInventory, ITickable, IInteractionObject {

	protected final ItemStack[] inventory;
	
	private String customName;

	public TileEntityGuiBase() {
		inventory = createInventory();
	}
	
	protected abstract ItemStack[] createInventory();
	
	public abstract String getDefaultName();

	@Override
	public String getName() {
		return hasCustomName() ? customName : getDefaultName();
	}

	@Override
	public boolean hasCustomName() {
		return customName != null;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public boolean isEmpty() {
		for(int i = 0; i < inventory.length; i++)
			if(!inventory[i].isEmpty())
				return false;
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		checkIndex(index);
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		checkIndex(index);
		ItemStack original = inventory[index];
		if(original.getCount() > count){
			original.setCount(original.getCount() - count);
			return new ItemStack(original.getItem(), count, original.getMetadata(), original.getTagCompound());
		}
		inventory[index] = ItemStack.EMPTY;
		return original;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		checkIndex(index);
		ItemStack stack = inventory[index];
		inventory[index] = ItemStack.EMPTY;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		checkIndex(index);
		inventory[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(getPos()) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public abstract boolean isItemValidForSlot(int index, ItemStack stack);

	@Override
	public void clear() {
		for(int i = 0; i < inventory.length; i++)
			inventory[i] = ItemStack.EMPTY;
	}
	
	protected void checkIndex(int index){
		if(index < 0 || index >= inventory.length)
			throw new IllegalArgumentException("inventory length is " + inventory.length + " and index is " + index);
	}

	@Override
	public abstract int[] getSlotsForFace(EnumFacing side);

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public abstract boolean canExtractItem(int index, ItemStack stack, EnumFacing direction);
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt = super.writeToNBT(nbt);
		if(customName != null)
			nbt.setString("CustomName", customName);
		for(int i = 0; i < inventory.length; i++){
			if(!inventory[i].isEmpty()){
				NBTTagCompound nbtSub = new NBTTagCompound();
				inventory[i].writeToNBT(nbtSub);
				nbt.setTag("Stack" + i, nbtSub);
			}
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		customName = nbt.getString("CustomName");
		if(customName.isEmpty())
			customName = null;
		for(int i = 0; i < inventory.length; i++){
			NBTTagCompound nbtSub = nbt.getCompoundTag("Stack" + i);
			if(!nbtSub.hasNoTags())
				inventory[i] = new ItemStack(nbtSub);
		}
	}
	
	@Override
	public ITextComponent getDisplayName(){
        return new TextComponentString(getName());
    }
}
