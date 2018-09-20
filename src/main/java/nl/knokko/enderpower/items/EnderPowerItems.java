package nl.knokko.enderpower.items;

import java.lang.reflect.Field;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.knokko.enderpower.EnderPower;
import nl.knokko.enderpower.energy.EnergyType;

public class EnderPowerItems {
	
	public static final Item THAU_DUST = new ItemParam("thau_dust");
	public static final Item SIE_DUST = new ItemParam("sie_dust");
	public static final Item FIE_DUST = new ItemParam("fie_dust");
	public static final Item GEE_DUST = new ItemParam("gee_dust");
	public static final Item DOU_DUST = new ItemParam("dou_dust");
	public static final Item ENDER_DUST = new ItemParam("ender_dust");
	public static final Item THAU_POWDER = new ItemParam("thau_powder");
	public static final Item SIE_POWDER = new ItemParam("sie_powder");
	public static final Item FIE_POWDER = new ItemParam("fie_powder");
	public static final Item GEE_POWDER = new ItemParam("gee_powder");
	public static final Item DOU_POWDER = new ItemParam("dou_powder");
	public static final Item ENDER_POWDER = new ItemParam("ender_powder");
	public static final Item THAU_BATTERY = new ItemEnergy("thau_battery", EnergyType.THAU, 1000);
	public static final Item SIE_BATTERY = new ItemEnergy("sie_battery", EnergyType.SIE, 1000);
	public static final Item FIE_BATTERY = new ItemEnergy("fie_battery", EnergyType.FIE, 1000);
	public static final Item GEE_BATTERY = new ItemEnergy("gee_battery", EnergyType.GEE, 1000);
	public static final Item DOU_BATTERY = new ItemEnergy("dou_battery", EnergyType.DOU, 1000);
	public static final Item ENDER_BATTERY = new ItemEnergy("ender_battery", EnergyType.ENDER, 1000);
	public static final Item MINING_LASER = new ItemMiningLaser("mining_laser", EnergyType.FIE, 2500, 50, 50);
	
	public static void init(){
		try {
			Field[] fields = EnderPowerItems.class.getFields();
			for(Field field : fields)
				register((Item) field.get(null));
		} catch(Exception ex){
			throw new RuntimeException("Failed to register the items of mod enderpower:", ex);
		}
	}
	
	public static void preInit(){
		try {
			Field[] fields = EnderPowerItems.class.getFields();
			for(Field field : fields)
				registerPre((Item) field.get(null));
		} catch(Exception ex){
			throw new RuntimeException("Failed to register the items of mod enderpower:", ex);
		}
	}
	
	private static void register(Item item){
		EnderPower.proxy.register(item);
	}
	
	private static void registerPre(Item item){
		GameRegistry.register(item);
	}
}
