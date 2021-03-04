package me.bscal.healthy.common.events.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerTickCallback
{
	Event<PlayerTickCallback> EVENT = EventFactory.createArrayBacked(PlayerTickCallback.class,
			(listeners) -> (player) -> {
				for (PlayerTickCallback listener : listeners)
				{
					ActionResult res = listener.tick(player);

					if (res != ActionResult.PASS)
						return res;
				}

				return ActionResult.PASS;
			});

	ActionResult tick(PlayerEntity player);
}
