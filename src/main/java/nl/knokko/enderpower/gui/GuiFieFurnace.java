package nl.knokko.enderpower.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.tileentity.TileEntityEnderFurnace;

public class GuiFieFurnace extends GuiEnderFurnace {
	
	private static final ResourceLocation FIE_FURNACE_GUI_TEXTURES = new ResourceLocation(EnderPower.MODID + ":textures/gui/fie_furnace.png");

	public GuiFieFurnace(InventoryPlayer playerInv, TileEntityEnderFurnace furnaceInv) {
		super(playerInv, furnaceInv);
	}
	
	@Override
	protected ResourceLocation getTexture(){
		return FIE_FURNACE_GUI_TEXTURES;
	}
}
