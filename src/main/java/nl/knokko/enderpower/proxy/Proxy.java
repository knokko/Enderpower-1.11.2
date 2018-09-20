package nl.knokko.enderpower.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;

public abstract class Proxy {

	public Proxy() {}
	
	public abstract Side getSide();
	
	public abstract void register(Block block);
	
	public abstract void register(Item item);
}
