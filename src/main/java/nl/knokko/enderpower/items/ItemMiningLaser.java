package nl.knokko.enderpower.items;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nl.knokko.enderpower.energy.EnergyType;

public class ItemMiningLaser extends ItemEnergy {
	
	private int energyPerShot;
	private int maxDistance;

	public ItemMiningLaser(String name, EnergyType energyType, int maxEnergy, int energyPerShot, int maxDistance) {
		super(name, energyType, maxEnergy);
		this.energyPerShot = energyPerShot;
		this.maxDistance = maxDistance;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote){
			if(getStoredEnergy(stack, EnergyType.FIE) >= energyPerShot){
				RayTraceResult trace = rayTrace(player, maxDistance, 1);
				if(trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK){
					BlockPos hit = trace.getBlockPos();
					IBlockState state = world.getBlockState(hit);
					if(state.getBlockHardness(world, hit) >= 0){//test for unbreakable
						stack.setItemDamage(stack.getItemDamage() + energyPerShot);
						world.setBlockToAir(hit);
						List<ItemStack> drops = state.getBlock().getDrops(world, hit, state, 0);
						for(ItemStack drop : drops)
							player.inventory.addItemStackToInventory(drop);
						return new ActionResult(EnumActionResult.SUCCESS, stack);
					}
				}
			}
			return new ActionResult(EnumActionResult.FAIL, stack);
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	public static RayTraceResult rayTrace(EntityLivingBase entity, double blockReachDistance, float partialTicks){
        Vec3d vec3d = entity.getPositionEyes(partialTicks);
        Vec3d vec3d1 = entity.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * blockReachDistance, vec3d1.yCoord * blockReachDistance, vec3d1.zCoord * blockReachDistance);
        return entity.world.rayTraceBlocks(vec3d, vec3d2, false, false, false);
    }
}
