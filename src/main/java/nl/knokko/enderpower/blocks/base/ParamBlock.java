package nl.knokko.enderpower.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import nl.knokko.enderpower.EnderPower;

public class ParamBlock extends Block {

	public ParamBlock(String name, Material material, SoundType sound, float hardness, float resistance) {
		super(material);
		this.setRegistryName(EnderPower.MODID, name);
		this.setUnlocalizedName(name);
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(sound);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	public ParamBlock(String name, Material material, SoundType sound, float hardness, float resistance, String tool, int toolLevel){
		this(name, material, sound, hardness, resistance);
		this.setHarvestLevel(tool, toolLevel);
	}
	
	public ParamBlock(String name, Material material, SoundType sound, float hardness, float resistance, String tool, int toolLevel, int lightLevel){
		this(name, material, sound, hardness, resistance, tool, toolLevel);
		this.setLightLevel(lightLevel);
	}
}
