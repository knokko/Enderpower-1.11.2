package nl.knokko.enderpower.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemParamBlock extends ItemBlock {

	public ItemParamBlock(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}

}
