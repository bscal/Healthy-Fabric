package me.bscal.healthy.common.events;

import me.bscal.healthy.Healthy;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

public class ServerStartedListener implements ServerLifecycleEvents.ServerStarted
{

	@Override
	public void onServerStarted(MinecraftServer server)
	{
		Healthy.SetServer(server);
		server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(true, server);
		Healthy.LOGGER.info("TICKS = " + server.getGameRules().get(GameRules.RANDOM_TICK_SPEED));
	}
}
