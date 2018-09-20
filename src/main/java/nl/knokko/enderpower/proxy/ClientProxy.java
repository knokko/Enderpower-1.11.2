package nl.knokko.enderpower.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;
import nl.knokko.enderpower.items.EnderPowerItems;

public class ClientProxy extends Proxy {

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}
	
	@Override
	public void register(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@Override
	public void register(Block block){
		register(Item.getItemFromBlock(block));
	}
}
