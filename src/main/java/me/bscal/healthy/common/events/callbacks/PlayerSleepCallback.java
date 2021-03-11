package me.bscal.healthy.common.events.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface PlayerSleepCallback
{
	/**
	 * Callback for player waking up
	 * Called at top of PlayerEntity wakeUp
	 * Upon return:
	 * - SUCCESS cancels further processing and continues
	 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
	 * - FAIL cancels further processing and does not continues calls in wakeUP
	 */
	Event<PlayerSleepCallback> EVENT = EventFactory.createArrayBacked(PlayerSleepCallback.class,
			(listeners) -> (player, world) -> {
				for (PlayerSleepCallback listener : listeners)
				{
					ActionResult res = listener.onSleep(player, player.world);

					if (res != ActionResult.PASS)
						return res;
				}
				return ActionResult.PASS;
			});

	ActionResult onSleep(PlayerEntity player, World world);

}
