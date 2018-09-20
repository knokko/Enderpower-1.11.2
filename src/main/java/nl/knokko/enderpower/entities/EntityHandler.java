package nl.knokko.enderpower.entities;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import nl.knokko.enderpower.EnderPower;

public final class EntityHandler {
	
	private static int entityID;
	
	private static void registerEntity(Class entityClass, String name){
		EntityRegistry.registerModEntity(new ResourceLocation(EnderPower.MODID, ":textures/entities/" + name), entityClass, name, entityID, EnderPower.modInstance, 64, 1, true);
		entityID++;
	}
	
	public static void register(){
		
	}
}
