package nl.knokko.enderpower.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import nl.knokko.enderpower.EnderPower;

public class ItemParam extends Item {

	public ItemParam(String name) {
		super();
		this.setRegistryName(EnderPower.MODID, name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.MATERIALS);
	}
}
