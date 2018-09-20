package nl.knokko.enderpower.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;
import nl.knokko.enderpower.tileentity.TileEntityFieFurnace;

public class BlockFieFurnace extends BlockEnderFurnace {
	
	private static boolean keepInventory;
	
	public static void setState(boolean active, World worldIn, BlockPos pos){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;
        if (active){
            worldIn.setBlockState(pos, EnderPowerBlocks.FIE_FURNACE_ON.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, EnderPowerBlocks.FIE_FURNACE_ON.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else {
            worldIn.setBlockState(pos, EnderPowerBlocks.FIE_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, EnderPowerBlocks.FIE_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        keepInventory = false;
        if (tileentity != null){
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

	public BlockFieFurnace(boolean on) {
		super(on, "fie_furnace");
	}
	
	 @Override
	 @SideOnly(Side.CLIENT)
	 @SuppressWarnings("incomplete-switch")
	 public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
	     if (this == EnderPowerBlocks.FIE_FURNACE_ON){
	         EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
	         double d0 = (double)pos.getX() + 0.5D;
	         double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
	         double d2 = (double)pos.getZ() + 0.5D;
	         double d3 = 0.52D;
	         double d4 = rand.nextDouble() * 0.6D - 0.3D;
	         if(rand.nextDouble() < 0.1D)
	             worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
	         switch(enumfacing){
	             case WEST:
	                 worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
	             case EAST:
	                 worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
	             case NORTH:
	                 worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
	             case SOUTH:
	                 worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
	         }
	     }
	 }
	 
	 @Override
	 public TileEntity createNewTileEntity(World world, int meta){
	     return new TileEntityFieFurnace();
	 }
	 
	 @Override
	 public void breakBlock(World worldIn,  BlockPos pos, IBlockState state){
	     if (!keepInventory){
	         TileEntity tileentity = worldIn.getTileEntity(pos);
	         if (tileentity instanceof TileEntityFieFurnace){
	             InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFieFurnace)tileentity);
	             worldIn.updateComparatorOutputLevel(pos, this);
	         }
	     }
	     super.breakBlock(worldIn, pos, state);
	 }
	 
	 @Override
	    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
	        if (!world.isRemote)
	            player.openGui(EnderPower.modInstance, 1, world, pos.getX(), pos.getY(), pos.getZ());
	        return true;
	    }
}
