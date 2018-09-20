package nl.knokko.enderpower.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.knokko.enderpower.container.slots.SlotEnergyItem;
import nl.knokko.enderpower.container.slots.SlotGeneratorFuel;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.tileentity.TileEntityGenerator;
import nl.knokko.enderpower.tileentity.base.TileEntityPanel;

public class ContainerGenerator extends Container {
	
	private final TileEntityGenerator generator;
	
	private int burnType;
	
	private int thau;
	private int sie;
	private int fie;
	private int gee;
	private int dou;
	private int ender;
	
	private char duration;
	private char fullDuration;

	public ContainerGenerator(InventoryPlayer playerInventory, TileEntityGenerator generator) {
		this.generator = generator;
		addSlotToContainer(new SlotGeneratorFuel(generator, 0, 8, 17));
		addSlotToContainer(new SlotEnergyItem(generator, 1, 134, 56));
		for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int k = 0; k < 9; ++k)
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return generator.isUsableByPlayer(player);
	}
	
	@Override
	public void addListener(IContainerListener listener){
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.generator);
    }
	
	@Override
	public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for (int i = 0; i < this.listeners.size(); ++i){
            IContainerListener icontainerlistener = (IContainerListener)listeners.get(i);
            if(duration != generator.getField(0))
                icontainerlistener.sendProgressBarUpdate(this, 0, generator.getField(0));
            if(thau != generator.getField(1))
            	icontainerlistener.sendProgressBarUpdate(this, 1, generator.getField(1));
            if(sie != generator.getField(2))
            	icontainerlistener.sendProgressBarUpdate(this, 2, generator.getField(2));
            if(fie != generator.getField(3))
            	icontainerlistener.sendProgressBarUpdate(this, 3, generator.getField(3));
            if(gee != generator.getField(4))
            	icontainerlistener.sendProgressBarUpdate(this, 4, generator.getField(4));
            if(dou != generator.getField(5))
            	icontainerlistener.sendProgressBarUpdate(this, 5, generator.getField(5));
            if(ender != generator.getField(6))
            	icontainerlistener.sendProgressBarUpdate(this, 6, generator.getField(6));
            if(fullDuration != generator.getField(7))
            	icontainerlistener.sendProgressBarUpdate(this, 7, generator.getField(7));
            if(burnType != generator.getField(8))
            	icontainerlistener.sendProgressBarUpdate(this, 8, generator.getField(8));
        }
        duration = (char) generator.getField(0);
        thau = generator.getField(1);
        sie = generator.getField(2);
        fie = generator.getField(3);
        gee = generator.getField(4);
        dou = generator.getField(5);
        ender = generator.getField(6);
        fullDuration = (char) generator.getField(7);
        burnType = generator.getField(8);
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.generator.setField(id, data);
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
