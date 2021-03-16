package me.bscal.healthy.common.events;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.health.HealthProvider;
import me.bscal.healthy.common.components.health.HealthUtils;
import me.bscal.healthy.common.components.health.IHealthComponent;
import me.bscal.healthy.common.components.injuries.InjuryProvider;
import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.Iterator;

public class PlayerTickListener implements PlayerTickCallback
{
	@Override
	public ActionResult tick(PlayerEntity player)
	{
		if (!player.world.isClient)
		{
			IHealthComponent health = HealthProvider.HEALTH.get(player);
			health.UpdateHealth(player);

			if (Healthy.GetServer().getTicks() % 20 == 0)
			{
				InjuryProvider.INJURY.get(player).UpdateInjuries();

				BlockPos playerPos = player.getBlockPos();
				Iterable<BlockPos> blocksAround = BlockPos.iterateOutwards(playerPos, 3,3 ,3);
				Iterator<BlockPos> blocksIterator = blocksAround.iterator();
				while (blocksIterator.hasNext())
				{
					final BlockPos blockPos = blocksIterator.next().toImmutable();
					BlockState state = player.getEntityWorld().getBlockState(blockPos);
					Block block = state.getBlock();
					if (blockPos.isWithinDistance(playerPos, 3.0))
					{
						if (block instanceof CampfireBlock && state.get(Properties.LIT))
						{
							HealthUtils.FoodToHealthTick(player, 1.0f, 1.0f, false);
							Healthy.LOGGER.info("Near a campfire! " + player.getPos().distanceTo(Vec3d.ofCenter(blockPos)));
							break;
						}
					}
				}
			}
		}
		return ActionResult.PASS;
	}
}
