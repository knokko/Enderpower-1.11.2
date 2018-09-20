package nl.knokko.enderpower.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.BlockEnderFurnace;
import nl.knokko.enderpower.blocks.BlockFieFurnace;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;
import nl.knokko.enderpower.container.ContainerEnderFurnace;
import nl.knokko.enderpower.energy.EnergyData;
import nl.knokko.enderpower.energy.EnergyStorageSingle;
import nl.knokko.enderpower.energy.EnergyType;

public class TileEntityFieFurnace extends TileEntityEnderFurnace {
	
	public TileEntityFieFurnace(){
		this.energy = new EnergyStorageSingle(EnergyType.FIE, 10000);
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player){
		return new ContainerEnderFurnace(inventory, this);
	}
	
	@Override
	public String getGuiID() {
		return EnderPower.MODID + ":fiefurnace";
	}
	
	@Override
	protected void setState(boolean on){
		if(!world.isRemote)
			BlockFieFurnace.setState(on, world, pos);
	}
	
	@Override
	protected boolean getState(){
		return world.getBlockState(pos).getBlock() == EnderPowerBlocks.FIE_FURNACE_ON;
	}
	
	@Override
	public String getDefaultName() {
		return "Fie Furnace";
	}
}
