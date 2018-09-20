package nl.knokko.enderpower.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.knokko.enderpower.blocks.base.BlockGui;
import nl.knokko.enderpower.tileentity.TileEntityEnderPanel;

public class BlockEnderPanel extends BlockGui {

	public BlockEnderPanel() {
		super("ender_panel", Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityEnderPanel();
	}

	@Override
	public int getGuiID() {
		return 2;
	}

}
