package me.bscal.healthy;

import me.bscal.healthy.common.events.PlayerTickListener;
import me.bscal.healthy.common.events.ServerStartedListener;
import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import me.bscal.healthy.common.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Healthy implements ModInitializer
{
	public static final String MOD_ID = "healthy";
	public static final String MOD_NAME = "Healthy";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
	public static final boolean DEBUG = true;

	@Override
	public void onInitialize()
	{
		ItemRegistry.Register();

		ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedListener());
		PlayerTickCallback.EVENT.register(new PlayerTickListener());
	}
}
