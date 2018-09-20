package nl.knokko.enderpower.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import nl.knokko.enderpower.energy.EnergyType;
import static nl.knokko.enderpower.items.EnderPowerItems.*;
import static nl.knokko.enderpower.energy.EnergyType.*;

public class GeneratorRecipes {
	
	private static Map<Item,Output> map = new HashMap<Item,Output>();
	
	public static boolean isFuel(Item item){
		return map.containsKey(item);
	}
	
	public static Output getFuelOutput(Item item){
		return map.get(item);
	}
	
	private static void add(Item item, EnergyType type, char value){
		map.put(item, new Output(type, value));
	}
	
	static void register(){
		add(THAU_DUST, THAU, (char) 40);
		add(SIE_DUST, SIE, (char) 40);
		add(FIE_DUST, FIE, (char) 40);
		add(GEE_DUST, GEE, (char) 40);
		add(DOU_DUST, DOU, (char) 40);
		add(ENDER_DUST, ENDER, (char) 40);
		add(THAU_POWDER, THAU, (char) 20);
		add(SIE_POWDER, SIE, (char) 20);
		add(FIE_POWDER, FIE, (char) 20);
		add(GEE_POWDER, GEE, (char) 20);
		add(DOU_POWDER, DOU, (char) 20);
		add(ENDER_POWDER, ENDER, (char) 20);
		MinecraftServer server = null;
		server.getNetworkSystem().addLanEndpoint(address, port);
	}
	
	public static class Output {
		
		private final EnergyType type;
		
		private final char duration;
		
		private Output(EnergyType type, char duration){
			this.type = type;
			this.duration = duration;
		}
		
		public EnergyType getType(){
			return type;
		}
		
		public char getDuration(){
			return duration;
		}
	}
}
