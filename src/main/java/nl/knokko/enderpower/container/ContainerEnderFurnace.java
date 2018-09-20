package nl.knokko.enderpower.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.knokko.enderpower.container.slots.SlotEnergyItem;

public class ContainerEnderFurnace extends Container {
	
	private final IInventory inventory;
	
    private int progress;
    private int energy;

    public ContainerEnderFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory){
        inventory = furnaceInventory;
        addSlotToContainer(new Slot(furnaceInventory, 0, 56, 35));
        addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 1, 116, 35));
        addSlotToContainer(new SlotEnergyItem(furnaceInventory, 2, 81, 61));
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int k = 0; k < 9; ++k)
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
    }

    public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.inventory);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
            if (this.progress != this.inventory.getField(0))
                icontainerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
            if(this.energy != this.inventory.getField(2))
            	icontainerlistener.sendProgressBarUpdate(this, 2, this.inventory.getField(2));
        }
        this.progress = inventory.getField(0);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.inventory.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer player){
        return this.inventory.isUsableByPlayer(player);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index == 1){
                if(!this.mergeItemStack(itemstack1, 2, 39, true))
                    return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(index != 0){
                if(!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty()){
                    if(!this.mergeItemStack(itemstack1, 0, 1, false))
                        return ItemStack.EMPTY;
                }
                else if(index >= 2 && index < 30){
                    if(!this.mergeItemStack(itemstack1, 30, 39, false))
                        return ItemStack.EMPTY;
                }
                else if(index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 30, false))
                    return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(itemstack1, 2, 39, false))
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
