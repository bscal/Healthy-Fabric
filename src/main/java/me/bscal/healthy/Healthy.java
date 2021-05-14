package me.bscal.healthy;

import me.bscal.healthy.common.commands.BasicCommands;
import me.bscal.healthy.common.components.injuries.InjuryRegistry;
import me.bscal.healthy.common.events.PlayerSleepListener;
import me.bscal.healthy.common.events.PlayerTickListener;
import me.bscal.healthy.common.events.ServerStartedListener;
import me.bscal.healthy.common.events.UseBlockListener;
import me.bscal.healthy.common.events.callbacks.PlayerSleepCallback;
import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import me.bscal.healthy.common.registry.BlockRegistry;
import me.bscal.healthy.common.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Healthy implements ModInitializer
{
	public static final String MOD_ID = "healthy";
	public static final String MOD_NAMESPACE = "healthy_additions";
	public static final String MOD_NAME = "Healthy";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
	public static final boolean DEBUG = true;
	public static final Random RAND = new Random();
	//public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID);

	//public static Config CONFIG;
	private static MinecraftServer m_server;

	@Override
	public void onInitialize()
	{
		//CONFIG = Config.builder(MOD_ID).add(new HealthyConfig()).build();

		//RRPCallback.EVENT.register((list -> list.add(RESOURCE_PACK)));
		ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedListener());
		PlayerTickCallback.EVENT.register(new PlayerTickListener());
		PlayerSleepCallback.EVENT.register(new PlayerSleepListener());
		UseBlockCallback.EVENT.register(new UseBlockListener());

		BasicCommands cmds = new BasicCommands();
		cmds.Register();

		BlockRegistry.Register();
		ItemRegistry.Register();
		InjuryRegistry.Register();
	}

	public static MinecraftServer GetServer() { return m_server; }

	public static void SetServer(MinecraftServer server) { m_server = server; }
}
