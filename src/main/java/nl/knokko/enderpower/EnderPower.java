package nl.knokko.enderpower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;
import nl.knokko.enderpower.entities.EntityHandler;
import nl.knokko.enderpower.items.EnderPowerItems;
import nl.knokko.enderpower.proxy.Proxy;
import nl.knokko.enderpower.recipes.EnderRecipes;
import nl.knokko.enderpower.tileentity.base.EnderPowerTileEntities;
import nl.knokko.enderpower.worldgen.EnderPowerGenerator;

@Mod(modid = EnderPower.MODID, name = EnderPower.NAME, version = EnderPower.VERSION)
public class EnderPower {
	
    public static final String MODID = "enderpower";
    public static final String NAME = "Enderpower";
    public static final String VERSION = "1.11.2.1";
    
    @SidedProxy(clientSide = "nl.knokko.enderpower.proxy.ClientProxy", serverSide = "nl.knokko.enderpower.proxy.ServerProxy")
    public static Proxy proxy;
    
    @Instance
    public static EnderPower modInstance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	EnderPowerBlocks.preInit();
    	EnderPowerItems.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	EnderPowerBlocks.init();
    	EnderPowerItems.init();
    	EnderRecipes.register();
    	EnderPowerGenerator.register();
    	EnderPowerTileEntities.register();
    	EntityHandler.register();
    }
}
