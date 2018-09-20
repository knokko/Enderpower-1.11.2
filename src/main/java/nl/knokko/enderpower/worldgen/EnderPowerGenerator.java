package nl.knokko.enderpower.worldgen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.knokko.enderpower.blocks.base.EnderPowerBlocks;

public class EnderPowerGenerator implements IWorldGenerator {
	
	private void generateSurface(World world, Random random, int BlockX, int BlockZ) {}
	
	private void generateEnd(World world, Random random, int BlockX, int BlockZ){
		for (int i = 0; i < 10; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(255);
			new WorldGenEnderOres(EnderPowerBlocks.ENDER_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
	}
	
	private void generateNether(World world, Random random, int BlockX, int BlockZ) {
		for (int i = 0; i < 5; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(255);
			new WorldGenNetherOres(EnderPowerBlocks.DOU_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
		for (int i = 0; i < 5; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(128);
			new WorldGenNetherOres(EnderPowerBlocks.GEE_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
		for (int i = 0; i < 5; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(128);
			new WorldGenNetherOres(EnderPowerBlocks.SIE_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
		for (int i = 0; i < 5; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(128);
			new WorldGenNetherOres(EnderPowerBlocks.THAU_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
		for (int i = 0; i < 5; i++) {
			int Xcoord = BlockX + random.nextInt(16);
			int Zcoord = BlockZ + random.nextInt(16);
			int Ycoord = random.nextInt(128);
			new WorldGenNetherOres(EnderPowerBlocks.FIE_ORE.getDefaultState(), 20).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
		}
	}


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
		switch (world.provider.getDimension()) {
			case 0:
				generateSurface( world, random, chunkX * 16, chunkZ * 16);
			case -1:
				generateNether( world, random, chunkX * 16, chunkZ * 16);
			case 1:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	
	public static void register(){
		GameRegistry.registerWorldGenerator(new EnderPowerGenerator(), 2);
	}
}
