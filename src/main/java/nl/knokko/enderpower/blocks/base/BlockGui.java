package nl.knokko.enderpower.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.knokko.enderpower.EnderPower;

public abstract class BlockGui extends BlockTileEntity {

	public BlockGui(String name, Material material) {
		super(name, material);
	}
	
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!world.isRemote)
            player.openGui(EnderPower.modInstance, getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    
    public abstract int getGuiID();
}
