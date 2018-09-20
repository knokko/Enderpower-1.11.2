package nl.knokko.enderpower.energy;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import nl.knokko.enderpower.EnderPower;

public class EnergyData extends WorldSavedData {
	
	private final Map<String,EnderEnergyStorage> energy = new TreeMap<String,EnderEnergyStorage>();
	
	public static EnderEnergyStorage getEnderStorage(String frequency){
		EnergyData instance = getInstance();
		EnderEnergyStorage storage = instance.energy.get(frequency);
		if(storage != null)
			return storage;
		storage = new EnderEnergyStorage(instance);
		instance.energy.put(frequency, storage);
		return storage;
	}
	
	private static EnergyData getInstance(){
		return getInstance(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0));
	}
	
	private static EnergyData getInstance(World world){
		EnergyData data = (EnergyData) world.loadData(EnergyData.class, EnderPower.MODID);
		if(data == null){
			data = new EnergyData(EnderPower.MODID);
			world.setData(EnderPower.MODID, data);
		}
		return data;
	}
	
	public static long drainEnergyAround(World world, BlockPos pos, EnergyType type, long amount){
		long current = 0;
		current += requestEnergy(world, pos.north(), type, amount);
		if(current >= amount)
			return current;
		current += requestEnergy(world, pos.east(), type, amount);
		if(current >= amount)
			return current;
		current += requestEnergy(world, pos.south(), type, amount);
		if(current >= amount)
			return current;
		current += requestEnergy(world, pos.west(), type, amount);
		if(current >= amount)
			return current;
		current += requestEnergy(world, pos.up(), type, amount);
		if(current >= amount)
			return current;
		current += requestEnergy(world, pos.down(), type, amount);
		if(current >= amount)
			return current;
		return current;
	}
	
	private static long requestEnergy(World world, BlockPos pos, EnergyType type, long amount){
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof IEnergyStorage)
			return ((IEnergyStorage) te).drainEnergy(type, amount);
		return 0;
	}

	public EnergyData(String id) {
		super(id);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		energy.clear();
		Iterator<String> iterator = nbt.getKeySet().iterator();
		while(iterator.hasNext()){
			String frequency = iterator.next();
			energy.put(frequency, new EnderEnergyStorage(nbt.getLong(frequency + EnergyType.THAU), nbt.getLong(frequency + EnergyType.SIE), nbt.getLong(frequency + EnergyType.FIE), nbt.getLong(frequency + EnergyType.GEE), nbt.getLong(frequency + EnergyType.DOU), nbt.getLong(frequency + EnergyType.ENDER)));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		System.out.println(energy.size());
		Iterator<Entry<String, EnderEnergyStorage>> iterator = energy.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, EnderEnergyStorage> entry = iterator.next();
			for(EnergyType type : EnergyType.values())
				nbt.setLong(entry.getKey() + type, entry.getValue().getStoredEnergy(type));
		}
		return nbt;
	}
}
