package me.bscal.healthy.common.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

public class ServerStartedListener implements ServerLifecycleEvents.ServerStarted
{

	@Override
	public void onServerStarted(MinecraftServer server)
	{
		server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(false, server);
	}
}
