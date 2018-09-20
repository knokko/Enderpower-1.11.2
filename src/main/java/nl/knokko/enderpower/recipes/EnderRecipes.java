package nl.knokko.enderpower.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import static nl.knokko.enderpower.blocks.base.EnderPowerBlocks.*;
import static nl.knokko.enderpower.items.EnderPowerItems.*;
import static net.minecraft.init.Blocks.*;

public class EnderRecipes {
	
	public static void register(){
		GeneratorRecipes.register();
		addOreSmelting(THAU_ORE, THAU_DUST);
		addOreSmelting(SIE_ORE, SIE_DUST);
		addOreSmelting(GEE_ORE, GEE_DUST);
		addOreSmelting(FIE_ORE, FIE_DUST);
		addOreSmelting(DOU_ORE, DOU_DUST);
		addOreSmelting(ENDER_ORE, ENDER_DUST);
		addShapelessRecipe(THAU_POWDER, 2, THAU_DUST);
		addShapelessRecipe(SIE_POWDER, 2, SIE_DUST);
		addShapelessRecipe(GEE_POWDER, 2, GEE_DUST);
		addShapelessRecipe(FIE_POWDER, 2, FIE_DUST);
		addShapelessRecipe(DOU_POWDER, 2, DOU_DUST);
		addShapelessRecipe(ENDER_POWDER, 2, ENDER_DUST);
		addShapelessRecipe(GEE_BATTERY, GEE_POWDER);
		addShapedRecipe(ENDER_FURNACE, END_STONE, ENDER_POWDER, END_STONE, ENDER_POWDER, FURNACE, ENDER_POWDER, END_STONE, ENDER_POWDER, END_STONE);
		addShapedRecipe(FIE_FURNACE, NETHERRACK, FIE_POWDER, NETHERRACK, FIE_POWDER, FURNACE, FIE_POWDER, NETHERRACK, FIE_POWDER, NETHERRACK);
	}
	
	private static void addOreSmelting(Block ore, Item dust){
		GameRegistry.addSmelting(ore, new ItemStack(dust), 0.4f);
	}
	
	private static void addShapelessRecipe(Item output, Item... input){
		GameRegistry.addShapelessRecipe(new ItemStack(output), (Object[]) input);
	}
	
	private static void addShapelessRecipe(Item output, int stackSize, Item... input){
		GameRegistry.addShapelessRecipe(new ItemStack(output, stackSize), (Object[]) input);
	}
	
	private static void addShapedRecipe(Block output, Object input00, Object input01, Object input02, Object input10, Object input11, Object input12, Object input20, Object input21, Object input22){
		addShapedRecipe(Item.getItemFromBlock(output), input00, input01, input02, input10, input11, input12, input20, input21, input22);
	}
	
	private static void addShapedRecipe(Item output, Object input00, Object input01, Object input02, Object input10, Object input11, Object input12, Object input20, Object input21, Object input22){
		GameRegistry.addShapedRecipe(new ItemStack(output), "abc", "def", "ghi", 'a', input00, 'b', input01, 'c', input02, 'd', input10, 'e', input11, 'f', input12, 'g', input20, 'h', input21, 'i', input22);
	}
}
