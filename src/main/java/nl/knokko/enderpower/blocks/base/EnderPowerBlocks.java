package nl.knokko.enderpower.blocks.base;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.*;
import nl.knokko.enderpower.items.ItemParamBlock;

public class EnderPowerBlocks {
	
	public static final Block THAU_ORE = new ParamBlock("thau_ore", Material.ROCK, SoundType.STONE, 1.5f, 10f, "pickaxe", 0, 7);
	public static final Block SIE_ORE = new ParamBlock("sie_ore", Material.ROCK, SoundType.STONE, 15f, 2000f, "pickaxe", 3, 7);
	public static final Block GEE_ORE = new ParamBlock("gee_ore", Material.ROCK, SoundType.STONE, 1.5f, 10f, "pickaxe", 0, 7);
	public static final Block FIE_ORE = new ParamBlock("fie_ore", Material.ROCK, SoundType.STONE, 1.5f, 10f, "pickaxe", 0, 7);
	public static final Block DOU_ORE = new ParamBlock("dou_ore", Material.ROCK, SoundType.STONE, 1.5f, 10f, "pickaxe", 0);
	public static final Block ENDER_ORE = new ParamBlock("ender_ore", Material.ROCK, SoundType.STONE, 2.5f, 20f, "pickaxe", 1);
	public static final Block ENDER_FURNACE = new BlockEnderFurnace(false);
	public static final Block ENDER_FURNACE_ON = new BlockEnderFurnace(true);
	public static final Block FIE_FURNACE = new BlockFieFurnace(false);
	public static final Block FIE_FURNACE_ON = new BlockFieFurnace(true);
	public static final Block ENDER_PANEL = new BlockEnderPanel();
	public static final Block FIE_PANEL = new BlockFiePanel();
	public static final Block GEE_PANEL = new BlockGeePanel();
	public static final Block DOU_PANEL = new BlockDouPanel();
	public static final Block GENERATOR = new BlockGenerator();
	
	public static void init(){
		try {
			Field[] fields = EnderPowerBlocks.class.getFields();
			for(Field field : fields)
				register((Block) field.get(null));
		} catch(Exception ex){
			throw new RuntimeException("Failed to register the blocks of mod enderpower:", ex);
		}
	}
	
	public static void preInit(){
		try {
			Field[] fields = EnderPowerBlocks.class.getFields();
			for(Field field : fields)
				registerPre((Block) field.get(null));
		} catch(Exception ex){
			throw new RuntimeException("Failed to register the blocks of mod enderpower:", ex);
		}
	}
	
	private static void registerPre(Block block){
		GameRegistry.register(block);
		GameRegistry.register(new ItemParamBlock(block));
	}
	
	private static void register(Block block){
		EnderPower.proxy.register(block);
	}
}
