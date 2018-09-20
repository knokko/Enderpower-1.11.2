package nl.knokko.enderpower.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.container.ContainerPanel;
import nl.knokko.enderpower.energy.EnergyStorageSingle;
import nl.knokko.enderpower.energy.EnergyType;
import nl.knokko.enderpower.tileentity.base.TileEntityPanel;

public class TileEntityEnderPanel extends TileEntityPanel {

	@Override
	public String getGuiID() {
		return EnderPower.MODID + ":enderpanel";
	}

	@Override
	protected EnergyStorageSingle createStorage() {
		return new EnergyStorageSingle(EnergyType.ENDER, 10000);
	}

	@Override
	protected boolean isPowered() {
		return world.provider.getDimension() == 1;
	}

	@Override
	protected long getPower() {
		return 1;
	}

	@Override
	public String getDefaultName() {
		return "Ender Panel";
	}

}
