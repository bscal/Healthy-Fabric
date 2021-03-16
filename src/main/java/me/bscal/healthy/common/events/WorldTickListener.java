package me.bscal.healthy.common.events;

import me.bscal.healthy.Healthy;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class WorldTickListener implements ServerTickEvents.StartWorldTick
{
	@Override
	public void onStartTick(ServerWorld world)
	{
	}
}
