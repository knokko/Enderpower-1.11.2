package nl.knokko.enderpower.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy extends Proxy {

	@Override
	public Side getSide() {
		return Side.SERVER;
	}

	@Override
	public void register(Block block) {}

	@Override
	public void register(Item item) {}
}
