package nl.knokko.enderpower.blocks.base;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.knokko.enderpower.EnderPower;

public abstract class BlockTileEntity extends BlockContainer {

	public BlockTileEntity(String name, Material material) {
		super(material);
		setCreativeTab(CreativeTabs.DECORATIONS);
		setUnlocalizedName(name);
		setRegistryName(EnderPower.MODID, name);
	}
	
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
}
