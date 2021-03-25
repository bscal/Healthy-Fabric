package me.bscal.healthy.common.events;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.IBurnableCampfireBlockEntity;
import me.bscal.healthy.common.config.HealthyConfig;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class UseBlockListener implements UseBlockCallback
{
	@Override
	public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult)
	{
		BlockState state = world.getBlockState(hitResult.getBlockPos());
		BlockEntity entity = world.getBlockEntity(hitResult.getBlockPos());
		ItemStack stack = player.getStackInHand(hand);
		Identifier handId = Registry.ITEM.getId(stack.getItem());
		boolean isFlintAndSteel = handId.toString().equals("minecraft:flint_and_steel");
		boolean isIgniter = handId.toString().equals("minecraft:stick") || isFlintAndSteel;

		if (!player.isSpectator() && state.getBlock().isIn(BlockTags.CAMPFIRES) && (isIgniter || HealthyConfig.RootGroup.CampfireItemsGroup.burnableItems.contains(handId.toString())))
		{
			IBurnableCampfireBlockEntity campfire = (IBurnableCampfireBlockEntity) entity;
			boolean lit = campfire.isLit();

			if (!lit && !isIgniter || lit && isFlintAndSteel)
				return ActionResult.PASS;

			// Logic is done on server. We still want to pass ActionResult.SUCCESS so a block does not
			// appear on the client side.
			if (!world.isClient)
			{
				ActionResult res = (lit) ?
						campfire.addFireTicks(stack.getItem(), 200) :
						campfire.tryFireIgnite(stack.getItem(), 200, .5f);

				if (res != ActionResult.PASS)
				{
					if (isFlintAndSteel)
						stack.damage(5, Healthy.RAND, (ServerPlayerEntity) player);
					else
						stack.decrement(1);
				}
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
