package nl.knokko.enderpower.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.knokko.enderpower.blocks.base.BlockGui;
import nl.knokko.enderpower.tileentity.TileEntityGeePanel;

public class BlockGeePanel extends BlockGui {

	public BlockGeePanel() {
		super("gee_panel", Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGeePanel();
	}

	@Override
	public int getGuiID() {
		return 4;
	}

}
