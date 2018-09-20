package nl.knokko.enderpower.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.base.BlockGui;
import nl.knokko.enderpower.tileentity.TileEntityGenerator;

public class BlockGenerator extends BlockGui {

	public BlockGenerator() {
		super("generator", Material.IRON);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGenerator();
	}

	@Override
	public int getGuiID() {
		return 6;
	}

}
