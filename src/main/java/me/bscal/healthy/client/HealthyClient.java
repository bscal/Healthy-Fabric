package me.bscal.healthy.client;

import com.mojang.brigadier.context.CommandContext;
import me.bscal.healthy.Healthy;
import me.bscal.healthy.client.gui.HealthyGUI;
import me.bscal.healthy.client.gui.HealthyScreen;
import me.bscal.healthy.client.gui.PlayerProperties;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT) public class HealthyClient implements ClientModInitializer
{

	public static final PlayerProperties PROPERTIES = new PlayerProperties();

	private int m_timer;

	@Override
	public void onInitializeClient()
	{
		ClientTickEvents.END_CLIENT_TICK.register((client) ->
		{
			if (client.player != null && m_timer++ % 20 == 0)
			{
				PROPERTIES.set(PlayerProperties.HP, Math.round(client.player.getHealth()));
				PROPERTIES.set(PlayerProperties.MAX_HP, Math.round(client.player.getMaxHealth()));
			}
		});

	}

}
