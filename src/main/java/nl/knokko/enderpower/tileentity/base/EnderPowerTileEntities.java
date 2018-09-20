package nl.knokko.enderpower.tileentity.base;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.tileentity.*;

import static nl.knokko.enderpower.EnderPower.MODID;

public final class EnderPowerTileEntities {
	
	public static void register(){
		GameRegistry.registerTileEntity(TileEntityEnderFurnace.class, MODID + ":enderfurnace");
		GameRegistry.registerTileEntity(TileEntityFieFurnace.class, MODID + ":fiefurnace");
		GameRegistry.registerTileEntity(TileEntityEnderPanel.class, MODID + ":enderpanel");
		GameRegistry.registerTileEntity(TileEntityFiePanel.class, MODID + ":fiepanel");
		GameRegistry.registerTileEntity(TileEntityGeePanel.class, MODID + ":geepanel");
		GameRegistry.registerTileEntity(TileEntityDouPanel.class, MODID + ":doupanel");
		GameRegistry.registerTileEntity(TileEntityGenerator.class, MODID + ":generator");
		NetworkRegistry.INSTANCE.registerGuiHandler(EnderPower.modInstance, new EnderGuiHandler());
	}
}
