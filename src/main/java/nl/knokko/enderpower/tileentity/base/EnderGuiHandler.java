package nl.knokko.enderpower.tileentity.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.*;
import nl.knokko.enderpower.gui.*;
import nl.knokko.enderpower.tileentity.*;

public class EnderGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if(entity != null){
			if(ID == 0 && entity instanceof TileEntityEnderFurnace)
				return new ContainerEnderFurnace(player.inventory, (TileEntityEnderFurnace) entity);
			if(ID == 1 && entity instanceof TileEntityFieFurnace)
				return new ContainerEnderFurnace(player.inventory, (TileEntityFieFurnace) entity);
			if(ID == 2 && entity instanceof TileEntityEnderPanel)
				return new ContainerPanel(player.inventory, (TileEntityEnderPanel) entity);
			if(ID == 3 && entity instanceof TileEntityFiePanel)
				return new ContainerPanel(player.inventory, (TileEntityFiePanel) entity);
			if(ID == 4 && entity instanceof TileEntityGeePanel)
				return new ContainerPanel(player.inventory, (TileEntityGeePanel) entity);
			if(ID == 5 && entity instanceof TileEntityDouPanel)
				return new ContainerPanel(player.inventory, (TileEntityDouPanel) entity);
			if(ID == 6 && entity instanceof TileEntityGenerator)
				return new ContainerGenerator(player.inventory, (TileEntityGenerator) entity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if(entity != null){
			if(ID == 0 && entity instanceof TileEntityEnderFurnace)
				return new GuiEnderFurnace(player.inventory, (TileEntityEnderFurnace) entity);
			if(ID == 1 && entity instanceof TileEntityFieFurnace)
				return new GuiFieFurnace(player.inventory, (TileEntityFieFurnace) entity);
			if(ID == 2 && entity instanceof TileEntityEnderPanel)
				return new GuiPanel(player.inventory, (TileEntityEnderPanel) entity, new ResourceLocation(EnderPower.MODID + ":textures/gui/ender_panel.png"));
			if(ID == 3 && entity instanceof TileEntityFiePanel)
				return new GuiPanel(player.inventory, (TileEntityFiePanel) entity, new ResourceLocation(EnderPower.MODID + ":textures/gui/fie_panel.png"));
			if(ID == 4 && entity instanceof TileEntityGeePanel)
				return new GuiPanel(player.inventory, (TileEntityGeePanel) entity, new ResourceLocation(EnderPower.MODID + ":textures/gui/gee_panel.png"));
			if(ID == 5 && entity instanceof TileEntityDouPanel)
				return new GuiPanel(player.inventory, (TileEntityDouPanel) entity, new ResourceLocation(EnderPower.MODID + ":textures/gui/dou_panel.png"));
			if(ID == 6 && entity instanceof TileEntityGenerator)
				return new GuiGenerator(player.inventory, (TileEntityGenerator) entity);
		}
		return null;
	}
}
