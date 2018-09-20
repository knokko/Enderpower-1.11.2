package nl.knokko.enderpower.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.knokko.enderpower.blocks.base.BlockGui;
import nl.knokko.enderpower.tileentity.TileEntityDouPanel;

public class BlockDouPanel extends BlockGui {

	public BlockDouPanel() {
		super("dou_panel", Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDouPanel();
	}

	@Override
	public int getGuiID() {
		return 5;
	}

}
