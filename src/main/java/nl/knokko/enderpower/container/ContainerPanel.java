package nl.knokko.enderpower.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.knokko.enderpower.container.slots.SlotEnergyItem;
import nl.knokko.enderpower.tileentity.base.TileEntityPanel;

public class ContainerPanel extends Container {
	
	private final TileEntityPanel inventory;
	
	private int energy;

	public ContainerPanel(InventoryPlayer playerInventory, TileEntityPanel inventory) {
		this.inventory = inventory;
		addSlotToContainer(new SlotEnergyItem(inventory, 0, 101, 31));
		for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int k = 0; k < 9; ++k)
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUsableByPlayer(player);
	}
	
	@Override
	public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.inventory);
    }
	
	@Override
	public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.energy != this.inventory.getField(0))
                icontainerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
        }
        this.energy = inventory.getField(0);
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.inventory.setField(id, data);
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index == 0){
                if(!this.mergeItemStack(itemstack1, 1, 39, true))
                    return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(!this.mergeItemStack(itemstack1, 1, 39, false))
                return ItemStack.EMPTY;
            if(itemstack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
            if(itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }
}
